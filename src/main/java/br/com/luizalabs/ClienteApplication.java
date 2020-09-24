package br.com.luizalabs;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import com.mongodb.client.MongoCollection;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.Role;
import br.com.luizalabs.usuarioautenticaco.v1.modelo.UsuarioAutenticacao;
import br.com.luizalabs.util.constants.RoleUtil;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableReactiveMongoRepositories
@Slf4j
public class ClienteApplication {
	
	private static final String[] MONGO_COLLECTIONS = {"clientes", "clientes_test", "user_auth"} ;

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

	@Bean
	@SuppressWarnings("unchecked")
	public <T> void createMongoDbConfig() {
		log.info("#################### CRIANDO AS COLLECTIONS DO MONGO ####################");
		Arrays.asList(MONGO_COLLECTIONS).forEach(collection -> {
			if (!mongoTemplate.getCollectionNames().contains(collection)) {
				MongoCollection<T> mongoCollection = (MongoCollection<T>) mongoTemplate.createCollection(collection);
				log.info("Collection criada com sucesso: " + collection);
			} else {
				log.info("Collection já está criada: " + collection);
			}
		});
		log.info("#########################################################################");
	}
	
	@Bean
	public void incluirUsuarioLogin() {
		Set<Role> roleList = new LinkedHashSet<>();
		roleList.add(new Role(RoleUtil.ROLE_ADMIN));
		roleList.add(new Role(RoleUtil.ROLE_USER));
		UsuarioAutenticacao usuario = new UsuarioAutenticacao("admin", "administratdor", new BCryptPasswordEncoder().encode("admin"), roleList);
		this.mongoTemplate.save(usuario);
		roleList.clear();
		roleList.add(new Role("ROLE_USER"));
		usuario = new UsuarioAutenticacao("user", "user", new BCryptPasswordEncoder().encode("api"), (Set<Role>) roleList);
		this.mongoTemplate.save(usuario);
		
	}
}
