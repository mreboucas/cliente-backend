package br.com.luizalabs.cliente.v1.service;

import org.springframework.stereotype.Service;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.cliente.v1.repository.ClienteRepository;
import br.com.luizalabs.util.exception.BusinessException;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:56:20
 */
@Service
public class ClienteCommandService {

	private final ClienteRepository clienteRepository;
	
	private final ClienteRulesService clienteRulesService;

	public ClienteCommandService(ClienteRepository clienteRepository, ClienteRulesService clienteRulesService) {
		this.clienteRulesService = clienteRulesService;
		this.clienteRepository = clienteRepository;
	}

	public void salvar(Cliente cliente) throws BusinessException {
		clienteRulesService.realizarValidacoes(cliente);
		this.clienteRepository.save(cliente).subscribe();
	}

	public void deletar(Cliente cliente) {
		this.clienteRepository.delete(cliente);
	}
}
