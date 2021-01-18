package com.agribay.agribayapp.config;

import javax.annotation.PostConstruct;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {
	private final ObjectMapper objectMapper;

	public JacksonConfiguration(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@PostConstruct // @PostConstruct annotation so we can configure Jackson’s ObjectMapper after
					// Spring Boot, because we don’t want to lose all useful setup made previously
					// by Spring Boot, such as Date and time serialization, for example.
	ObjectMapper jacksonObjectMapper() {
		objectMapper.registerModule(new JsonNullableModule());
		return objectMapper;
	}
}
