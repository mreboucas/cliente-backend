package br.com.luizalabs.cliente.v1.resource;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.cliente.v1.service.ClienteService;
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
@RequestMapping("v1/clientes")
public class ClienteResource {

	private ClienteService clientService;

	public ClienteResource(ClienteService clientService) {
		super();
		this.clientService = clientService;
	}
	

	@ApiOperation(value = "Salva um cliente", notes = "Responsável por inserir na base um cliente")
	@ApiResponses({@ApiResponse(code = 201, message = "Success")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public Mono<Void> salvar(@RequestBody @Valid Cliente cliente) {
		this.clientService.salvar(cliente);
		return Mono.empty();
	}
}
