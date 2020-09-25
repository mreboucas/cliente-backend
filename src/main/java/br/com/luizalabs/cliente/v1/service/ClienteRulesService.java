package br.com.luizalabs.cliente.v1.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import br.com.luizalabs.cliente.v1.model.ClienteDTO;
import br.com.luizalabs.cliente.v1.model.ProdutoFavorito;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.service.ProdutoQueryService;
import br.com.luizalabs.util.exception.BusinessException;
import br.com.luizalabs.util.exceptionhandler.Erro;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 15:56:20
 */
@Service
@Slf4j
public class ClienteRulesService {

	private final ClienteQueryService clienteQueryService;
	private final ProdutoQueryService produtoQueryService;
	
	private final int badRequestCode = HttpStatus.BAD_REQUEST.value();

	public ClienteRulesService(ClienteQueryService clienteQueryService, ProdutoQueryService produtoQueryService) {
		this.clienteQueryService = clienteQueryService;
		this.produtoQueryService = produtoQueryService;
	}

	public void realizarValidacoes(ClienteDTO cliente) throws BusinessException {
		verificarSeEmailJaEstaCadastrado(cliente);
		verificarSeOsProdutosExistemNaBase(cliente);
		verificarSeClientePossuiProdutoDuplicado(cliente);
	}
	/**
	 * 
	 * @INFO: essa validação poderia ter sido realizada, também, via mongo Index. Gostou? Legal demais!
	 * @link: https://docs.mongodb.com/manual/core/index-unique/ 
	 *
	 * @param cliente
	 * @throws BusinessException
	 */
	private void verificarSeEmailJaEstaCadastrado(ClienteDTO cliente) throws BusinessException {

		ClienteDTO clienteEmailParam = new ClienteDTO();
		clienteEmailParam.setEmail(cliente.getEmail());
		Flux<ClienteDTO> clienteAux = clienteQueryService.listarPorParametros(clienteEmailParam);

		if (clienteAux != null && clienteAux.count().block() > 0) {
			Optional<ClienteDTO> cliOptional = clienteAux.collectList().block().stream().filter(cli -> !cli.getId().equals(cliente.getId())).findFirst();

			if (cliOptional.isPresent()) {
				throw new BusinessException(new Erro(badRequestCode, "Email já cadastrado", "Email já cadastrado", "email"));
			}
		}
	}

	private void verificarSeClientePossuiProdutoDuplicado(ClienteDTO cliente) throws BusinessException {

		if (!CollectionUtils.isEmpty(cliente.getProdutoFavoritoList())) {
			Set<Erro> erroSet = new LinkedHashSet<>();
			List<ProdutoFavorito> produtoFavoritoAuxList = cliente.getProdutoFavoritoList();
			if (cliente.getId() != null) {
				Mono<ClienteDTO> clienteMono = this.clienteQueryService.findById(cliente.getId());
				ClienteDTO clienteDb = clienteMono.block();
				if (clienteDb != null && !CollectionUtils.isEmpty(clienteDb.getProdutoFavoritoList())) {
					produtoFavoritoAuxList.addAll(clienteDb.getProdutoFavoritoList());
				}
			}

			Set<String> produtoFavoritoSet = new LinkedHashSet<>();
			cliente.getProdutoFavoritoList().forEach(produtoFavorito -> {
				if (!produtoFavoritoSet.add(produtoFavorito.getIdProduto())) {
					Erro erro = new Erro(badRequestCode, "Produto de id: " + produtoFavorito.getIdProduto() + " já foi adicionado", "produtoFavoritoList:idProduto", "Produto duplicado");
					erroSet.add(erro);
				}
			});
			if (!CollectionUtils.isEmpty(erroSet)) {
				throw new BusinessException(new ArrayList<>(erroSet));
			}
		}
	}
	
	@SuppressWarnings("restriction")
	private void verificarSeOsProdutosExistemNaBase(ClienteDTO cliente) throws BusinessException {
		
		if (!CollectionUtils.isEmpty(cliente.getProdutoFavoritoList())) {
			List<Erro> erroList = new ArrayList<>();
			cliente.getProdutoFavoritoList().forEach(produto -> {
				Mono<ProdutoDTO> produtoMonoDb = Mono.empty();
				try {
					produtoMonoDb = this.produtoQueryService.consultarProdutoPorId(produto.getIdProduto());
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				ProdutoDTO produtoAux = produtoMonoDb.block();
				if (produtoAux == null) {
					Erro erro = new Erro(badRequestCode, "Produto não existente na base de dados", "Produto não existente na base de dados", "produtoFavoritoList:idProduto");
					erro.setValue(produto.getIdProduto());
					erroList.add(erro);
				} else {
					produto.setNome(produtoAux.getTitle());
				}
			});
			if (!CollectionUtils.isEmpty(erroList)) {
				throw new BusinessException(erroList);
			}
		}
	}
	
	public void validarExclusaoCliente(ClienteDTO cliente) throws BusinessException {
		if (cliente == null || !StringUtils.isNotBlank(cliente.getId())) {
			throw new BusinessException(new Erro(badRequestCode, "Cliente não pode ser excluído, id obrigatório", null, "id"));
		}
		Mono<ClienteDTO> cliAux = clienteQueryService.findById(cliente.getId());
		
		if (cliAux.block() == null) {
			Erro erro = new Erro(badRequestCode, "Cliente não pode ser excluído, pois não foi encontrado na base de dados com esse id", null, null);
			erro.setValue(cliente.getId());
			throw new BusinessException(erro);
		}
	}
}
