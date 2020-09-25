package br.com.luizalabs.cliente.v1.resource;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.luizalabs.cliente.v1.model.ClienteDTO;
import br.com.luizalabs.cliente.v1.service.ClienteQueryService;
import br.com.luizalabs.util.constants.RoleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import reactor.core.publisher.Flux;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 10:23:06
 */
@Api(tags = {"Clientes"})
@SwaggerDefinition(tags = {@Tag(name = "Clientes")})
@RestController
@RequestMapping("v1/cliente")
public class ClienteQueryResource {

	private ClienteQueryService clienteQueryService;

	public ClienteQueryResource(ClienteQueryService clienteQueryService) {
		super();
		this.clienteQueryService = clienteQueryService;
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	@PreAuthorize("hasRole('" + RoleUtil.ROLE_ADMIN_SPRING +"')" + " && hasRole('" + RoleUtil.ROLE_USER_SPRING +"')")
	public Flux<ClienteDTO> consultar(@RequestParam Optional<String> nome, @RequestParam Optional<String> email) {
		ClienteDTO cliente = new ClienteDTO(null, nome.isPresent() ? nome.get() : null, email.isPresent() ? email.get() : null, null);
		return this.clienteQueryService.listarPorParametros(cliente);
	}
}
