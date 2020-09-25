package br.com.luizalabs.cliente.v1.service;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import br.com.luizalabs.cliente.v1.model.ClienteDTO;
import br.com.luizalabs.cliente.v1.repository.ClienteRepository;
import br.com.luizalabs.util.mongo.CriteriaMongoUtil;
import br.com.luizalabs.util.mongo.enumeration.ParameterMongoEnum;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 15:56:20
 */
@Service
public class ClienteQueryService {

	private final ClienteRepository clienteRepository;

	private final MongoTemplate mongoTemplate;

	public ClienteQueryService(ClienteRepository clienteRepository, MongoTemplate mongoTemplate) {
		this.clienteRepository = clienteRepository;
		this.mongoTemplate = mongoTemplate;
	}

	public Flux<ClienteDTO> listarTodos() {
		return clienteRepository.findAll();
	}

	public Flux<ClienteDTO> listarPorParametros(ClienteDTO cliente) {

		Query query = new Query();
		query.limit(30);

		if (StringUtils.isNotBlank(cliente.getNome())) {
			Criteria criteria = CriteriaMongoUtil.getCriteria(cliente.getNome(), "nome", ParameterMongoEnum.LIKE_IGNORE_CASE);
			query.addCriteria(criteria);
		}

		if (StringUtils.isNotBlank(cliente.getEmail())) {
			Criteria criteria = CriteriaMongoUtil.getCriteria(cliente.getEmail(), "email", ParameterMongoEnum.EQUAL);
			query.addCriteria(criteria);
		}

		List<ClienteDTO> clientList = this.mongoTemplate.find(query, ClienteDTO.class);

		return Flux.fromIterable(clientList);
	}
	
	public Mono<ClienteDTO> findById(String id) {
		return this.clienteRepository.findById(id);
	}

}
