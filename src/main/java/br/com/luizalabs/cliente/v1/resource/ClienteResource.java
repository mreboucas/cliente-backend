package br.com.luizalabs.cliente.v1.resource;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.service.ProdutoService;
import br.com.luizalabs.util.exception.PaginaProdutoInvaldaException;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:47:41 
 */
@RestController
@RequestMapping("v1/cliente")
public class ClienteResource {

	private ProdutoService produtoService;

	public ClienteResource(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}
	
	@ResponseStatus(code = HttpStatus.CREATED)
	private void salvar(Cliente cliente) {
		
	}
	
	@GetMapping("/produtos/{pagina}")
	public List<ProdutoDTO> listProdutos(@PathVariable("pagina") int pagina) throws PaginaProdutoInvaldaException {
		return produtoService.consultarProdutoPorPagina(pagina);
		
	}
}
