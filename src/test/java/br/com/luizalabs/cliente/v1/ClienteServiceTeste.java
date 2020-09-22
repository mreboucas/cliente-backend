package br.com.luizalabs.cliente.v1;

import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import br.com.luizalabs.cliente.v1.model.Cliente;
import br.com.luizalabs.cliente.v1.service.ClienteService;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 21 de set de 2020 as 16:44:11 
 */
@Service
@Getter
@Setter
public class ClienteServiceTeste {
	
	private final ClienteService clienteService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static final String COLLECTION_CLIENTE_TEST = "clientes_test";
	
	public ClienteServiceTeste(ClienteService clienteService) {
		super();
		this.clienteService = clienteService;
	}
	
	public Cliente salvar(Cliente cliente) {
		return this.mongoTemplate.save(cliente, COLLECTION_CLIENTE_TEST);
	}
	
	public long deletarTodos() {
		MongoDatabase db = this.mongoTemplate.getDb();
		MongoCollection<Document> collection = db.getCollection(COLLECTION_CLIENTE_TEST);
		Bson filter = new Document();
		DeleteResult deleteResult = collection.deleteMany(filter);
		return deleteResult.getDeletedCount();
	}
	
	public List<Cliente> buscarTodos() {
		return mongoTemplate.findAll(Cliente.class, COLLECTION_CLIENTE_TEST);
	}
}