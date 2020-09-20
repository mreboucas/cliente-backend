package br.com.luizalabs.produto.v1.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.dto.ProdutoPaginadoDTO;
import br.com.luizalabs.produto.v1.service.ProdutoService;
import br.com.luizalabs.util.exception.JsonProcessExceptionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 10:23:06
 */
@Api(tags = {"Produtos"}, description = "Busca de Produtos")
@SwaggerDefinition(tags = {@Tag(name = "Produtos")})
@RestController
@RequestMapping("v1/produto")
public class ProdutoResource {

	private ProdutoService produtoService;

	public ProdutoResource(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}

	@ApiOperation(value = "Lista de produtos por página", notes = "Recupera todos os produtos por página. Query parameter = ?pagina=numpagina", response = ProdutoPaginadoDTO[].class)
	@ApiResponses({@ApiResponse(code = 200, message = "Success", response = ProdutoPaginadoDTO[].class)})
	@GetMapping
	public Flux<ProdutoPaginadoDTO> listProdutos(@RequestParam(defaultValue = "1") int pagina) throws JsonProcessExceptionException {
		return produtoService.consultarProdutoPorPagina(pagina);

	}

	@ApiOperation(value = "Lista um Produto", notes = "Recupera um produto por seu código", response = ProdutoDTO[].class)
	@ApiResponses({@ApiResponse(code = 200, message = "Success", response = ProdutoDTO[].class)})
	@GetMapping("/{id}")
	public Mono<ProdutoDTO> listProdutosPorId(@PathVariable("id") String idProduto) {
		return produtoService.consultarProdutoPorId(idProduto);

	}
}
