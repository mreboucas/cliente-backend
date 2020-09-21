package br.com.luizalabs.produto.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 20 de set de 2020 as 06:47:15 
 */

@ApiModel(value = "Informação da página do Produto", description = "Informação Páginas Produto API Magalu/LuizaLabs")
@Setter
@Getter
public class PaginaInfoDto {
	
	@ApiModelProperty(value = "número do página")
	@JsonProperty("page_number")
	private Long pageNumber;
	
	@ApiModelProperty(value = "total de páginas")
	@JsonProperty("page_size")
	private Long pageSize;
	
}