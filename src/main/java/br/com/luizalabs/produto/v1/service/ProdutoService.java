package br.com.luizalabs.produto.v1.service;

import java.util.List;
import org.springframework.stereotype.Service;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.feign.ProdutoFeign;
import br.com.luizalabs.util.exception.PaginaProdutoInvaldaException;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 06:19:09
 */
@Service
public class ProdutoService {
	
	private final ProdutoFeign produtoFeign;

	public ProdutoService(ProdutoFeign produtoFeign) {
		this.produtoFeign = produtoFeign;
	}

	public List<ProdutoDTO> consultarProdutoPorPagina(int pagina) throws PaginaProdutoInvaldaException {

		if (pagina <= 0) {
			throw new PaginaProdutoInvaldaException();
		}

		List<ProdutoDTO> produtoDTOList = produtoFeign.consultarPorPagina(pagina);

		return produtoDTOList;

	}

	public ProdutoDTO consultarProdutoPorId(int idProduto) {

		ProdutoDTO produtoDTO = produtoFeign.consultarPorIdProduto(idProduto);

		return produtoDTO;

	}
}
