package br.com.springboot.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * Spring Boot application starter class
 */
@EntityScan(basePackages = "br.com.springboot.projeto.model")
@SpringBootApplication
@ComponentScan({ "br.com.springboot.projeto.controllers"})
@EnableJpaRepositories(basePackages = "br.com.springboot.projeto.repository")
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}
