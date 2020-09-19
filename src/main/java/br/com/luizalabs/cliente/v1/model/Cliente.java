package br.com.luizalabs.cliente.v1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:45:13 
 */
@Setter
@Getter
@Document(collection = "cliente")
public class Cliente {
	
	@Id
	private String id;
	
	private String nome;
	
	private String email;

}
