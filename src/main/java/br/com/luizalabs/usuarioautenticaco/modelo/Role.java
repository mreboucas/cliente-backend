package br.com.luizalabs.usuarioautenticaco.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 17:16:16 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String role;

	@Override
	public String getAuthority() {
		return this.role;
	}
}