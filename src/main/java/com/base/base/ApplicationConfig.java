package com.base.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class ApplicationConfig {
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		mapper.setDateFormat(dateFormat);
		return mapper;
	}
}
