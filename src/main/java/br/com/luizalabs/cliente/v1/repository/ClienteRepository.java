package br.com.luizalabs.cliente.v1.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import br.com.luizalabs.cliente.v1.model.Cliente;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:56:09 
 */
public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String> {
}
