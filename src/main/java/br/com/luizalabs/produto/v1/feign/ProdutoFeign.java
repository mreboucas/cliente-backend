package br.com.luizalabs.produto.v1.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 05:57:47 
 */
@FeignClient(url = "http://challenge-api.luizalabs.com/api/product", name = "Api-luizalabs", decode404 = true)
public interface ProdutoFeign {
	
	/**
	 *	<b>This method represents:</b> 
	 * URL: http://challenge-api.luizalabs.com/api/product/?page=<PAGINA>
	 * 
	 * <PAGINA> representa o número da página requisitada, iniciando em 1.
	 * 
	 * @param List<ProdutoDTO>
	 */
	@GetMapping
	public List<ProdutoDTO> consultarPorPagina(@RequestParam(value = "page") int pagina);
	
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
	@GetMapping("/{id}")
	public ProdutoDTO consultarPorIdProduto(@PathVariable(value = "id") int idProduto);
}