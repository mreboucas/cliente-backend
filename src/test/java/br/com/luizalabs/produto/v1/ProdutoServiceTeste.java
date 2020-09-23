package br.com.luizalabs.produto.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.luizalabs.produto.v1.service.ProdutoQueryService;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 15:25:41 
 */
@Service
public class ProdutoServiceTeste {
	
	@Autowired
	public ProdutoQueryService produtoService;

}
