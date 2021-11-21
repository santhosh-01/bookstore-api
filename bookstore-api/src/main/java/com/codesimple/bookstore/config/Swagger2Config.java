package com.codesimple.bookstore.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class Swagger2Config {
	
	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/**"))
				.build()
				.apiInfo(new ApiInfo("My API Documetation", 
						"This is customized API Documentation", 
						"1.0", 
						"#", 
						new Contact("Admin", "#", "santhosh.110999@gmail.com"), 
						"opensource", 
						"#", 
						Collections.emptyList()));
		
	}

}