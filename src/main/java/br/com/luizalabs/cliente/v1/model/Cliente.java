package br.com.luizalabs.cliente.v1.model;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 04:45:13 
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Cliente", description = "Cliente API Magalu/LuizaLabs")
@Document(collection = "clientes")
public class Cliente {
	
	@ApiModelProperty("Identificador do cliente")
	@Id
	private String id;
	
	@ApiModelProperty("Nome do cliente")
	@NotNull(message = "O nome não pode ser nulo")
	@NotEmpty(message = "O nome não pode ser vazio")
	@Size(min = 6, max = 50)
	@Valid
	private String nome;
	
	@ApiModelProperty("Email do cliente")
	@Email(message = "Email deve ser válido")
	@Valid
	private String email;
	
	@Valid
	@ApiModelProperty("Listagem dos produtos favoritos do cliente")
	private List<ProdutoFavorito> produtoFavoritoList;

}
