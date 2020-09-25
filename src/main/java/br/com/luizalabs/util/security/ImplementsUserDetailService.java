package br.com.luizalabs.util.security;

import java.util.Optional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacaoDTO;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacaoDetails;
import br.com.luizalabs.usuarioautenticaco.v1.repository.UsuarioAutenticacoRepository;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:53:40
 */
@Component
public class ImplementsUserDetailService implements UserDetailsService {

	private final UsuarioAutenticacoRepository usuarioRepository;

	public ImplementsUserDetailService(UsuarioAutenticacoRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		/** List<GrantedAuthority> gratAuthoritieList = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER"); */

		Mono<UsuarioAutenticacaoDTO> usuarioAutenticacaoMono = Optional.ofNullable(this.usuarioRepository.findByUserName(userName)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado pelo user name"));
		
		UsuarioAutenticacaoDTO usuarioAutenticacao = usuarioAutenticacaoMono.block();
		
		UsuarioAutenticacaoDetails usuario = new ObjectMapper().convertValue(usuarioAutenticacao, UsuarioAutenticacaoDetails.class);

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
	}
}