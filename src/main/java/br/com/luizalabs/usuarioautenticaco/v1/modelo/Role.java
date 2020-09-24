package br.com.luizalabs.usuarioautenticaco.v1.modelo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 17:16:16 
 */
@ApiModel(value = "Role do usuário de autenticação", description = "Role do usuário de autenticação")
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
	@NotEmpty
	@NotNull
	@ApiModelProperty("Role de acesso: ROLE_ADMIN ou ROLE_USER")
	private String role;

	@Override
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	public String getAuthority() {
		return this.role;
	}
}