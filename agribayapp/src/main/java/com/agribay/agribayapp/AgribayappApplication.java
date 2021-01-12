package com.agribay.agribayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync       // This will run mail sending functionality asynchronusly
public class AgribayappApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgribayappApplication.class, args);
	}

}
