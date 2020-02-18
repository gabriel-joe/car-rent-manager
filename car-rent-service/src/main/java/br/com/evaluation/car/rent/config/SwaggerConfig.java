package br.com.evaluation.car.rent.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	
	@Bean
    public Docket api() { 
    	
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("br.com.evaluation.car.rent.controller"))
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                           
    }
	    
    @SuppressWarnings("rawtypes")
	private ApiInfo apiInfo() {
		Collection<VendorExtension> list = new ArrayList<>();
    	Contact contact = new Contact("RENTAL CARS", "https://github.com/gabriel-joe", "gabrielsaantos02@gmail.com");
        return new ApiInfo(
                "RENTAL CARS",
                "Description of API.",
                "1.0.0",
                "Terms of service",
                contact,
                "License of API",
                "https://github.com/gabriel-joe", 
                list);
    }
}
