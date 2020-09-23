package br.com.luizalabs.cliente.v1.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.cliente.v1.model.ProdutoFavorito;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.service.ProdutoQueryService;
import br.com.luizalabs.util.exception.BusinessException;
import br.com.luizalabs.util.exceptionhandler.Erro;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 15:56:20
 */
@Service
public class ClienteRulesService {

	private final ClienteQueryService clienteQueryService;
	private final ProdutoQueryService produtoQueryService;
	
	private final static int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

	public ClienteRulesService(ClienteQueryService clienteQueryService, ProdutoQueryService produtoQueryService) {
		this.clienteQueryService = clienteQueryService;
		this.produtoQueryService = produtoQueryService;
	}

	public void realizarValidacoes(Cliente cliente) throws BusinessException {
		verificarSeEmailJaEstaCadastrado(cliente);
		verificarSeOsProdutosExistemNaBase(cliente);
		verificarSeClientePossuiProdutoDuplicado(cliente);
	}

	private void verificarSeEmailJaEstaCadastrado(Cliente cliente) throws BusinessException {

		Cliente clienteEmailParam = new Cliente();
		clienteEmailParam.setEmail(cliente.getEmail());
		Flux<Cliente> clienteAux = clienteQueryService.listarPorParametros(clienteEmailParam);

		if (clienteAux != null && clienteAux.count().block() > 0) {
			Optional<Cliente> cliOptional = clienteAux.collectList().block().stream().filter(cli -> !cli.getId().equals(cliente.getId())).findFirst();

			if (cliOptional.isPresent()) {
				throw new BusinessException(new Erro(BAD_REQUEST, "Email já cadastrado", "Email já cadastrado", "email"));
			}
		}
	}

	private void verificarSeClientePossuiProdutoDuplicado(Cliente cliente) throws BusinessException {

		if (!CollectionUtils.isEmpty(cliente.getProdutoFavoritoList())) {
			Set<Erro> erroSet = new LinkedHashSet<>();
			List<ProdutoFavorito> produtoFavoritoAuxList = cliente.getProdutoFavoritoList();
			if (cliente.getId() != null) {
				Mono<Cliente> clienteMono = this.clienteQueryService.findById(cliente.getId());
				Cliente clienteDb = clienteMono.block();
				if (clienteDb != null && !CollectionUtils.isEmpty(clienteDb.getProdutoFavoritoList())) {
					produtoFavoritoAuxList.addAll(clienteDb.getProdutoFavoritoList());
				}
			}

			Set<String> produtoFavoritoSet = new LinkedHashSet<>();
			cliente.getProdutoFavoritoList().forEach(produtoFavorito -> {
				if (!produtoFavoritoSet.add(produtoFavorito.getIdProduto())) {
					Erro erro = new Erro(BAD_REQUEST, "Produto de id: " + produtoFavorito.getIdProduto() + " já foi adicionado", "produtoFavoritoList:idProduto", "Produto duplicado");
					erroSet.add(erro);
				}
			});
			if (!CollectionUtils.isEmpty(erroSet)) {
				throw new BusinessException(new ArrayList<>(erroSet));
			}
		}
	}
	
	private void verificarSeOsProdutosExistemNaBase(Cliente cliente) throws BusinessException {
		
		if (!CollectionUtils.isEmpty(cliente.getProdutoFavoritoList())) {
			List<Erro> erroList = new ArrayList<>();
			cliente.getProdutoFavoritoList().forEach(produto -> {
				Mono<ProdutoDTO> produtoMonoDb = this.produtoQueryService.consultarProdutoPorId(produto.getIdProduto());
				if (produtoMonoDb == null || produtoMonoDb.block() == null) {
					Erro erro = new Erro(BAD_REQUEST, "Produto não existente na base de dados", "Produto não existente na base de dados", "produtoFavoritoList:idProduto");
					erro.setValue(produto.getIdProduto());
					erroList.add(erro);
				}
			});
			if (!CollectionUtils.isEmpty(erroList)) {
				throw new BusinessException(erroList);
			}
		}
	}
}
