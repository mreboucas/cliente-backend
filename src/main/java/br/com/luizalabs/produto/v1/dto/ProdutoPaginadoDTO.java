package br.com.luizalabs.produto.v1.dto;

import java.util.List;
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
@ApiModel(value = "Produto Paginado", description = "Produto Paginado API Magalu/LuizaLabs")
public class ProdutoPaginadoDTO {
	
	@ApiModelProperty("Informações da paginação")
	private PaginaInfoDto meta;
	
	@ApiModelProperty("Listagem dos produtos")
	private List<ProdutoDTO> products;

}