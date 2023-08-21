package br.com.b3;

import br.com.b3.entity.Company;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class B3Configurations {

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .additionalModels(typeResolver.resolve(Company.class))
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.b3.controller"))
                .paths(PathSelectors.ant("/statusinvest/**"))
                .build();
    }
}
