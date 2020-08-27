package com.ccs.creditcardapprove.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * This class provides the configuration for implementing Swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	/**
	 * This method creates object for Docket with the provided configuration at the time of dependency injection 
	 * for swagger implementation
	 * @return
	 */
	@Bean
	public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.ccs.creditcardapprove.controller"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
	

}
