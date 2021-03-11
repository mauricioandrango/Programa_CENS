package com.cens.backend.censbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
/*@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})*/
@EntityScan("com.cens.backend.censbackend.entities")
@EnableJpaRepositories("com.cens.backend.censbackend.repository")
@ComponentScan({"com.cens.backend.censbackend.controller"})

public class CensbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CensbackendApplication.class, args);
	}
	/*@RequestMapping("/hola")
	String hello() {
		return "Hello, world";
	}
	
	@RequestMapping("/user")
	String user() {
		return "{\n" + 
				"  \"userId\": 1,\n" + 
				"  \"id\": 1,\n" + 
				"  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" + 
				"  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" + 
				"}";
	}*/
}
