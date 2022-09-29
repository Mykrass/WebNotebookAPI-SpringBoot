package com.al3xkras.web_notebook_api.user_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EntityScan("com.al3xkras.web_notebook_api")
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}

}
