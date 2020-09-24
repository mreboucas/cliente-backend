package br.com.luizalabs.usuarioautenticaco.v1.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacao;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:51:24 
 */

public interface UsuarioRepository extends ReactiveCrudRepository<UsuarioAutenticacao, String> {

	Mono<UsuarioAutenticacao> findByUserName(String login);
}