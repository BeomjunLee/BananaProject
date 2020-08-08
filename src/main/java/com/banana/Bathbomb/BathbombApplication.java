package com.banana.Bathbomb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BathbombApplication {

	public static void main(String[] args) {
		SpringApplication.run(BathbombApplication.class, args);
	}

}
