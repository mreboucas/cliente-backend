package br.com.luizalabs.usuarioautenticaco.v1.modelo;

import java.util.Collection;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UsuarioAutenticacaoDetails implements UserDetails {
	
	private static final long serialVersionUID = 9157695151181913689L;

	@Id
	@NotEmpty
	@NotNull
	@ApiModelProperty("Login do usuário")
	private String userName;
	
	@ApiModelProperty("Nome completo")
	private String nomeCompleto;

	@NotEmpty
	@NotNull
	@ApiModelProperty("Senha do usuário")
	private String password;
	
	@Valid
	@NotNull(message= "roleList deve ser preenchido: ROLE_ADMIN ou ROLE_USER")
	@NotEmpty(message= "roleList deve ser preenchido: ROLE_ADMIN ou ROLE_USER")
	@ApiModelProperty("Roles de permissão do usuário: ROLE_ADMIN ou ROLE_USER")
	private Set<Role> roleList;
	
	@Override
	@ApiModelProperty(hidden = true)
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roleList;
	}

	@Override
	@ApiModelProperty(hidden = true)
	public String getPassword() {
		return this.password;
	}

	@Override
	@ApiModelProperty(hidden = true)
	public String getUsername() {
		return this.userName;
	}

	@Override
	@ApiModelProperty(hidden = true)
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@ApiModelProperty(hidden = true)
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@ApiModelProperty(hidden = true)
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@ApiModelProperty(hidden = true)
	public boolean isEnabled() {
		return true;
	}
	
	public void encriptyPassword() {
		this.password = new BCryptPasswordEncoder().encode(password);
	}
}
