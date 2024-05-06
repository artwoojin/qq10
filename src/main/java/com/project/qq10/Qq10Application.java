package com.project.qq10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class Qq10Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Qq10Application.class, args);
	}

}
