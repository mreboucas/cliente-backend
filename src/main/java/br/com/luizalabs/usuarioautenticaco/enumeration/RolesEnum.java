package br.com.luizalabs.usuarioautenticaco.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 23 de set de 2020 as 08:13:28 
 */
@AllArgsConstructor
@Getter
public enum RolesEnum {
	
	ROLE_ADMIN("ROLE_ADMIN", "ADMIN"),
	ROLE_USER("ROLE_USER", "USER"),;
	
	private String db;
	private String spring;

}
