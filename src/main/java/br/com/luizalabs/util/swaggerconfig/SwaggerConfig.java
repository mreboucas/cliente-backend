package br.com.luizalabs.util.swaggerconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicates;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Marcelo Reboucas - marceloreboucas10@gmail.com - 19 de set de 2020 as 05:36:28 
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	@Value("api.pom.version")
	private String apiPomVersion;
	
	@Bean
	public Docket taskApi() {
		return new Docket(DocumentationType.SWAGGER_2)
	         .useDefaultResponseMessages(false)
	         .apiInfo(apiInfo())
	         .select()
	         .apis(RequestHandlerSelectors.any())
		      .paths(Predicates.not(PathSelectors.regex("/error.*")))
	         .build();
	  }
	
	 private ApiInfo apiInfo() {
		 return new ApiInfoBuilder()
            .title("API de clientes")
            .description("API para administrar clientes e seus produtos favoritos.")
            .version(apiPomVersion)
            .build();
    }
}