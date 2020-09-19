package br.com.luizalabs.util.rest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 05:16:20 
 */
@Getter
@Setter
public class RetornoDto {

	private String messageForUser;
	
	private String nameField;
	
	private String messageForDeveloper;
	
	private String statusCode;
	
	private Object data;
	
}
