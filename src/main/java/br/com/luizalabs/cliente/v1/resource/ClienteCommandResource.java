package br.com.luizalabs.cliente.v1.resource;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.cliente.v1.service.ClienteCommandService;
import br.com.luizalabs.usuarioautenticaco.enumeration.RolesEnum;
import br.com.luizalabs.util.constants.RoleUtil;
import br.com.luizalabs.util.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 10:23:06
 */
@Api(tags = {"Clientes"}, description = "Manutenção de clientes")
@SwaggerDefinition(tags = {@Tag(name = "Clientes")})
@RestController
@RequestMapping("v1/cliente")
public class ClienteCommandResource {

	private ClienteCommandService clienteCommandService;

	public ClienteCommandResource(ClienteCommandService clienteCommandService) {
		super();
		this.clienteCommandService = clienteCommandService;
	}


	@ApiOperation(value = "Salva um cliente", notes = "Responsável por inserir na base um cliente")
	@ApiResponses({@ApiResponse(code = 201, message = "Criado"), @ApiResponse(code = 405, message = "Método informado na requisição não é o esperado pelo backend")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	@PreAuthorize("hasRole('" + RoleUtil.ROLE_ADMIN_SPRING +"')" + " && hasRole('" + RoleUtil.ROLE_USER_SPRING +"')")
	public Mono<Void> salvar(@Valid @RequestBody Cliente clienteDto) throws BusinessException {
		this.clienteCommandService.salvar(clienteDto);
		return Mono.empty();
	}

	@ApiOperation(value = "Atualiza um cliente", notes = "Responsável por atualizar na base um cliente")
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 405, message = "Método informado na requisição não é o esperado pelo backend")})
	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping
	@PreAuthorize("hasRole('" + RoleUtil.ROLE_ADMIN_SPRING +"')" + " && hasRole('" + RoleUtil.ROLE_USER_SPRING +"')")
	public Mono<Void> atualizar(@Valid @RequestBody Cliente clienteDto) throws BusinessException {
		this.clienteCommandService.salvar(clienteDto);
		return Mono.empty();
	}

	@ApiOperation(value = "Deleta um cliente", notes = "Responsável por deletar na base um cliente")
	@ApiResponses({@ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 405, message = "Método informado na requisição não é o esperado pelo backend")})
	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('" + RoleUtil.ROLE_ADMIN_SPRING +"')")
	public Mono<Void> deletar(@PathVariable (required = true) String id) throws BusinessException {
		this.clienteCommandService.deletar(id);
		return Mono.empty();
	}
}
