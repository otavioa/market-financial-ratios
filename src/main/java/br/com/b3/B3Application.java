package br.com.b3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.fasterxml.classmate.TypeResolver;

import br.com.b3.controller.dto.CompanyDTO;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class B3Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(B3Application.class, args);
	}
	
	@Bean
	public Docket api(TypeResolver typeResolver) {
	    return new Docket(DocumentationType.SWAGGER_2)
	      .additionalModels(typeResolver.resolve(CompanyDTO.class))
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("br.com.b3.controller"))
	      .paths(PathSelectors.ant("/statusinvest/**"))
	      .build();
	}

}
