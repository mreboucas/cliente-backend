package br.com.luizalabs.usuarioautenticaco.v1.service;

import org.springframework.stereotype.Service;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacaoDTO;
import br.com.luizalabs.usuarioautenticaco.v1.repository.UsuarioAutenticacoRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 25 de set de 2020 as 07:12:08
 */
@Service
@Slf4j
@Getter
public class UsuarioAutenticacoService {

	private final UsuarioAutenticacoRepository usuarioRepository;

	public UsuarioAutenticacoService(UsuarioAutenticacoRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}

	public void salvar(UsuarioAutenticacaoDTO usuarioAutenticacao) {
			usuarioAutenticacao.encriptyPassword();
			this.usuarioRepository.save(usuarioAutenticacao);
	}
}
	