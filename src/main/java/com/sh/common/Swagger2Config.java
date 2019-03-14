package com.sh.common;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {
 
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
//				.apis(RequestHandlerSelectors.basePackage("com.sh"))
//				.paths(PathSelectors.any())
				.paths(PathSelectors.ant("/api/**"))
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Note系统API文档")
				.description("restful风格，Note系统API文档")
				.termsOfServiceUrl("http://127.0.0.1:8081/")
				.version("1.0")
				.build();
	}
}
