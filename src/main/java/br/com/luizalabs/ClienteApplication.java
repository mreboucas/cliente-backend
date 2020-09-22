package br.com.luizalabs;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ClienteApplication {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);
	}

   @Bean 
   public RestTemplate restTemplate() {
       RestTemplate template = new RestTemplate();
       PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
       connectionManager.setMaxTotal(100);
       connectionManager.setDefaultMaxPerRoute(20);
       template.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClients.custom().setConnectionManager(connectionManager).build()));
       return template;
   }	
	/*
	 * @Bean public void createMongoDbConfig() {
	 * 
	 * if (!mongoTemplate.getCollectionNames().contains("clientes")) { MongoCollection collection = mongoTemplate.createCollection("clientes"); } }
	 */
}
