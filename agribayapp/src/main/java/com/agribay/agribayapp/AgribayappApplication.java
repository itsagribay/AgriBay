package com.agribay.agribayapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.agribay.agribayapp.config.SwaggerConfiguration;

@SpringBootApplication
@EnableAsync       // This will run mail sending functionality asynchronusly
@Import(SwaggerConfiguration.class)
public class AgribayappApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgribayappApplication.class, args);
	}

}
