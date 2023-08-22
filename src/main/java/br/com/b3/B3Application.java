package br.com.b3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@EnableMongoRepositories
public class B3Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(B3Application.class, args);
	}

}
