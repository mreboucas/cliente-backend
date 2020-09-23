package br.com.luizalabs.util.exceptionhandler;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 21 de set de 2020 as 19:24:31 
 */
@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class Erro implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int statusCode;
	private String messageForUser;
	private String nameField;
	private String value;
	private String messageForDeveloper;
	private String exception;
	
	public Erro(String messageForUser,String  messageForDeveloper,String nameField) {		
		this.messageForUser = messageForUser;
		this.nameField = nameField;
		this.messageForDeveloper = messageForDeveloper;
	}
	
	

	public Erro() {
	    super();
	}

	public Erro(int statusCode, String messageForUser, String nameField, String messageForDeveloper) {
	    super();
	    this.statusCode = statusCode;
	    this.messageForUser = messageForUser;
	    this.nameField = nameField;
	    this.messageForDeveloper = messageForDeveloper;
	}
}
