package br.com.luizalabs.usuarioautenticaco.v1.modelo;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 22 de set de 2020 as 16:47:09 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Usuário de autenticação", description = "Usuário que será utilizado para se logar no serviço")
@Document("user_auth")
public class UsuarioAutenticacaoDTO {
	
	@Id
	@NotEmpty
	@NotNull
	@ApiModelProperty("Login do usuário")
	private String userName;
	
	@ApiModelProperty("Nome completo")
	private String nomeCompleto;

	@NotEmpty
	@NotNull
	@Size(min = 6)
	@ApiModelProperty("Senha do usuário")
	private String password;
	
	@Valid
	@NotNull(message= "roleList deve ser preenchido: ROLE_ADMIN ou ROLE_USER")
	@NotEmpty(message= "roleList deve ser preenchido: ROLE_ADMIN ou ROLE_USER")
	@ApiModelProperty("Roles de permissão do usuário: ROLE_ADMIN ou ROLE_USER")
	private Set<Role> roleList;
	
	public void encriptyPassword() {
		this.password = new BCryptPasswordEncoder().encode(password);
	}
}
