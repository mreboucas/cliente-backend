package br.com.luizalabs.cliente.v1.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:45:13 
 */
@Setter
@Getter
@ApiModel(value = "Cliente", description = "Cliente API Magalu/LuizaLabs")
@Document(collection = "cliente")
public class Cliente {
	
	@ApiModelProperty("Identificador do cliente")
	@Id
	private String id;
	
	@ApiModelProperty("Nome do cliente")
	private String nome;
	
	@ApiModelProperty("Email do cliente")
	private String email;

}
