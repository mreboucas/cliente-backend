package br.com.luizalabs.usuarioautenticaco.v1.resource;

import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacao;
import br.com.luizalabs.usuarioautenticaco.v1.repository.UsuarioRepository;
import br.com.luizalabs.util.constants.RoleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 23 de set de 2020 as 14:09:39 
 */

@Api(tags = {"Usuário autenticação"}, description = "Manutenção dos usuários de login da API")
@SwaggerDefinition(tags = {@Tag(name = "Usuário")})
@RestController
@RequestMapping("/v1/usuarioautenticaco")
public class UsuarioAutenticacoResource {
	
	private UsuarioRepository usuarioRepository;

	public UsuarioAutenticacoResource(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@ApiOperation(value = "Salva um usuário", notes = "Responsável por inserir na base um usuário de autenticação")
	@PostMapping
	@PreAuthorize("hasRole('"+ RoleUtil.ROLE_ADMIN_SPRING +"')")
	public Mono<Void> salvar(@RequestBody @Valid UsuarioAutenticacao usuarioAutenticacao) {
		this.usuarioRepository.save(usuarioAutenticacao);
		return Mono.just(null);
	}
	
	@ApiOperation(value = "Busca um usuário pelo login", notes = "Busca um usuário pelo login")
	@GetMapping("/login/{login}")
	@PreAuthorize("hasRole('"+ RoleUtil.ROLE_ADMIN_SPRING +"')")
	public Mono<UsuarioAutenticacao> findByUserName(@RequestParam (required = true) String login) {
		return this.usuarioRepository.findByUserName(login);
	}
	/**
	 * @TODO Depois realizar a paginação. 
	 *
	 */
	@ApiOperation(value = "Busca todos os usuários", notes = "Busca todos os usuários")
	@GetMapping("/todos")
	@PreAuthorize("hasRole('"+ RoleUtil.ROLE_ADMIN_SPRING +"')")
	public Flux<UsuarioAutenticacao> buscarTodos() {
		return this.usuarioRepository.findAll();
	}
}
