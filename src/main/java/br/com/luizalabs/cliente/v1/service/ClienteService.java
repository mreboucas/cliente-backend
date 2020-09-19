package br.com.luizalabs.cliente.v1.service;

import org.springframework.stereotype.Service;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.cliente.v1.repository.ClienteRepository;
import reactor.core.publisher.Flux;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:56:20 
 */
@Service
public class ClienteService {
	
	private final ClienteRepository clienteRepository;
	
	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	public void salvar(Cliente cliente) {
		
	}
	
	public void deletar(Cliente cliente) {
		
	}
	
	public void atualizar(Cliente cliente) {
		
	}
	
	public Flux<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}
	
	public Flux<Cliente> listarPorParametros(Cliente cliente) {
		return null;
	}

}
