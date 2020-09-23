package br.com.luizalabs.usuarioautenticaco.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import br.com.luizalabs.usuarioautenticaco.modelo.UsuarioAutenticacao;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:51:24 
 */

public interface UsuarioRepository extends ReactiveMongoRepository<UsuarioAutenticacao, String> {

	UsuarioAutenticacao findByLogin(String login);
	
}
