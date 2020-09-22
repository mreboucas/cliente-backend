package br.com.luizalabs.produto.v1;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import br.com.luizalabs.produto.v1.dto.ProdutoDTO;
import br.com.luizalabs.produto.v1.dto.ProdutoPaginadoDTO;
import br.com.luizalabs.util.exception.JsonProcessExceptionException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 14:50:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class ProdutoTest {

	@Autowired
	private ProdutoServiceTeste produtoServiceTest;

	List<String> produtoCodList;
	
	List<Integer> paginaCodList;

	@Before
	public void preencheProdutosLista() {
		this.produtoCodList = new ArrayList<>();

		this.produtoCodList.add("eaefc867-10a6-3a5e-947d-43a984964fcf");
		this.produtoCodList.add("5ab740b8-4046-3f19-ae21-3302dd7abef8");
		this.produtoCodList.add("69e2f68f-20cc-b9c0-8365-89928a2dcf88");
		this.produtoCodList.add("919ae426-64d3-c0c4-3ca3-9d32f8df3453");
		
		this.paginaCodList = new ArrayList<>();
		this.paginaCodList.add(10);
		this.paginaCodList.add(250);
		
	}

	@Test
	public void consultarProdutosPeloId() {

		this.produtoCodList.forEach(idProduto -> {
			Mono<ProdutoDTO> produtoDTO = produtoServiceTest.produtoService.consultarProdutoPorId(idProduto);
			assertEquals(produtoDTO.block().getId(), idProduto);
		});
	}
	
	@Test
	public void consultarProdutoPaginado() {
		this.paginaCodList.forEach(pagina -> {
			Flux<ProdutoPaginadoDTO> produtoPaginadoDTO;
			try {
				produtoPaginadoDTO = produtoServiceTest.produtoService.consultarProdutoPorPagina(pagina);
				produtoPaginadoDTO.flatMap(product -> {
					assertEquals(product.getMeta().getPageNumber(), pagina);
					return Mono.just(product.getMeta().getPageNumber());
				});
			} catch (JsonProcessExceptionException e) {
				log.error(e.getMessage());
			}
		});
	}
}