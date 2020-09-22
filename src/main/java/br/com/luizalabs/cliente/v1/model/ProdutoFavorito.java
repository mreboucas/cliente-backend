package br.com.luizalabs.cliente.v1.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 21 de set de 2020 as 14:43:47 
 */
@ApiModel(value = "Produto favorito", description = "Produto favorito do cliente")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProdutoFavorito {
	
	@Valid
	@NotNull(message =  "idProduto deve ser diferente de null")
	@NotEmpty(message =  "idProduto deve ser diferente de vazio")
	@ApiModelProperty("Identificador do produto")
	private String idProduto;

	@ApiModelProperty("Nome do produto")
	private String nome;
}