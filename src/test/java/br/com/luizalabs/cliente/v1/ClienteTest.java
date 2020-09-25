package br.com.luizalabs.cliente.v1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import br.com.luizalabs.cliente.v1.model.ClienteDTO;
import br.com.luizalabs.cliente.v1.model.ProdutoFavorito;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 14:50:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClienteTest {

	private List<ClienteDTO> clienteList;
	private List<ProdutoFavorito> produtoFavoritoList;
	
	@Autowired
	private ClienteServiceTeste clienteServiceTest;

	@Before
	public void before() {
		this.clienteList = new ArrayList<>();
		this.produtoFavoritoList = new ArrayList<>();
		ProdutoFavorito produtoFavorito;

		produtoFavorito = new ProdutoFavorito("07902753-2c91-f5c3-1b9c-502ba4e7b631", null);
		produtoFavoritoList.add(produtoFavorito);
		produtoFavorito = new ProdutoFavorito("0462c6e7-71d9-bc1f-0224-3ddcfa7d794e", "Restaurador Capilar Extrait Phyto-Aromatique 30ml");
		produtoFavoritoList.add(produtoFavorito);
		ClienteDTO cliente = new ClienteDTO(null, "David Gilmour", "davidgilmour@pinkfloyd.com", produtoFavoritoList);
		clienteList.add(cliente);

		produtoFavoritoList = new ArrayList<>();
		produtoFavorito = new ProdutoFavorito("16dd1f48-35db-8133-2f33-1585a128f95c", null);
		produtoFavoritoList.add(produtoFavorito);
		produtoFavorito = new ProdutoFavorito("ae771c1b-5fc9-f7ef-fde1-dc2ee578bf96", null);
		produtoFavoritoList.add(produtoFavorito);
		cliente = new ClienteDTO(null, "Ozzy Osbourne", "ozzy@blacksabbath.com", produtoFavoritoList);
		clienteList.add(cliente);

	}

	@Test
	public void salvar() {
		this.clienteList.forEach(cliente -> {
			ClienteDTO clienteAux = this.clienteServiceTest.salvar(cliente);
			assertEquals(clienteAux.getNome(), cliente.getNome());
			assertNotNull(clienteAux.getId());
			assertNotNull(clienteAux.getProdutoFavoritoList());
		});
	}
	
	@Test
	public void deletarTodos() {
		List<ClienteDTO> clienteList = this.clienteServiceTest.buscarTodos();
		long qtdRemovida = this.clienteServiceTest.deletarTodos();
		assertEquals(clienteList != null ? clienteList.size() : 0, qtdRemovida);
	}
}