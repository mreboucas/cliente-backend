package br.com.luizalabs.util.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.luizalabs.usuarioautenticaco.modelo.UsuarioAutenticacao;
import br.com.luizalabs.usuarioautenticaco.repository.UsuarioRepository;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:53:40
 */
@Transactional
@Repository
public class ImplementsUserDetailService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public ImplementsUserDetailService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

		UsuarioAutenticacao usuario = this.usuarioRepository.findByLogin(login);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado pelo login");
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), usuario.isAccountNonExpired(), usuario.isCredentialsNonExpired(), usuario.isAccountNonLocked(), usuario.getAuthorities());
	}


}
