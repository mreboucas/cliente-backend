package br.com.luizalabs.produto.v1.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.dto.ProdutoPaginadoDTO;
import br.com.luizalabs.util.exception.JsonProcessExceptionException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 08:03:33
 */

@Component
@Slf4j
public class ProdutoRestClient {

	/** @See: application.properties */	
	@Value("${url.base.api}")
	private String urlBaseProdutoAPI;

	private final RestTemplate restTemplate;

	public ProdutoRestClient(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}
	
	/**
	 * <b>This method represents:</b>
	 * 
	 * http://challenge-api.luizalabs.com/api/product/<ID>/
	 * 
	 * <ID> representa o id do produto, a ser coletado no endpoint de listagem.
	 * 
	 * @param pagina
	 * @return ProdutoDTO
	 */
	public Mono<ProdutoDTO> consultarProdutoPorId(String idProduto) {

		ResponseEntity<ProdutoDTO> response = restTemplate.getForEntity(urlBaseProdutoAPI + idProduto + "/", ProdutoDTO.class);
		Mono<ProdutoDTO> produtoDTOMono = Mono.empty();

		if (HttpStatus.OK.value() == response.getStatusCode().value()) {
			
			produtoDTOMono = Mono.just(response.getBody());
			
		} else {
			log.error("Problema ao consultar o produto por id ou o código da resposta foi diferente de 200. Status code retorno:" + response.getStatusCode().value());
		}

		return produtoDTOMono;
	}

	/**
	 * <b>This method represents:</b> URL: http://challenge-api.luizalabs.com/api/product/?page=<PAGINA>
	 * 
	 * <PAGINA> representa o número da página requisitada, iniciando em 1.
	 * 
	 * @param List<ProdutoPaginadoDTO>
	 * @throws JsonProcessExceptionException
	 */
	public Flux<ProdutoPaginadoDTO> consultarProdutoPorPagina(int pagina) throws JsonProcessExceptionException {

		ResponseEntity<String> response = restTemplate.getForEntity(urlBaseProdutoAPI + "/?page=" + pagina, String.class);

		Flux<ProdutoPaginadoDTO> produtoPaginadoDTOFluxList = Flux.empty();

		if (HttpStatus.OK.value() == response.getStatusCode().value()) {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			try {
				
				List<ProdutoPaginadoDTO> produtoPaginadoDTOList = objectMapper.readValue(response.getBody(), new TypeReference<List<ProdutoPaginadoDTO>>() {});
				
				produtoPaginadoDTOFluxList = Flux.fromIterable(produtoPaginadoDTOList);
				
			} catch (JsonProcessingException e) {
				log.error("Erro ao fazer o parser do json para List<ProdutoPaginadoDTO>\n" + e.getMessage());
				throw new JsonProcessExceptionException(e.getMessage());
			}
		} else {
			log.error("Problema ao consultar a lista de produtos paginadas ou o código da resposta foi diferente de 200. Status code retorno:" + response.getStatusCode().value());
		}
		return produtoPaginadoDTOFluxList;
	}

}
