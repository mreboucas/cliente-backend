package br.com.luizalabs.produto.v1.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @API_DOC_LINK: https://gist.github.com/Bgouveia/9e043a3eba439489a35e70d1b5ea08ec
 * 
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 05:22:50 
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Produto", description = "Produto API Magalu/LuizaLabs")
public class ProdutoDTO {
	
	@ApiModelProperty(value = "id do produto")
	private String id;
	
	@ApiModelProperty(value = "preço do produto")
	private Double price;
	
	@ApiModelProperty(value = "URL da imagem do produto")
	private String image;
	
	@ApiModelProperty(value = "marca do produto")
	private String brand;
	
	@ApiModelProperty(value = "nome do produto")
	private String title;
	
	@ApiModelProperty(value = "média dos reviews para este produto")
	private String reviewScore;

}