package br.com.luizalabs.util.security;

import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacao;
import br.com.luizalabs.usuarioautenticaco.v1.repository.UsuarioRepository;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:53:40
 */
@Component
public class ImplementsUserDetailService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public ImplementsUserDetailService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		/** List<GrantedAuthority> gratAuthoritieList = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER"); */

		Mono<UsuarioAutenticacao> usuarioAutenticacaoMono = Optional.ofNullable(this.usuarioRepository.findByUserName(userName)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pelo user name"));
		
		UsuarioAutenticacao usuario = usuarioAutenticacaoMono.block();

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
	}
}