package br.com.luizalabs.produto.v1.service;

import org.springframework.stereotype.Service;
import br.com.luizalabs.produto.v1.client.ProdutoRestClient;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.dto.ProdutoPaginadoDTO;
import br.com.luizalabs.util.exception.JsonProcessExceptionException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 06:19:09
 */
@Service
public class ProdutoQueryService {
	
	private ProdutoRestClient produtoRestClient;

	public ProdutoQueryService(ProdutoRestClient produtoRestClient) {
		this.produtoRestClient = produtoRestClient;
	}

	public Flux<ProdutoPaginadoDTO> consultarProdutoPorPagina(int pagina) throws JsonProcessExceptionException {
		return this.produtoRestClient.consultarProdutoPorPagina(pagina);

	}

	public Mono<ProdutoDTO> consultarProdutoPorId(String idProduto) {
		return this.produtoRestClient.consultarProdutoPorId(idProduto);
	}
}