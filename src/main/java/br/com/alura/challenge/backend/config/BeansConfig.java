package br.com.alura.challenge.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class BeansConfig {
	
	@Bean
	public Gson gsonBuilder() {
		return new Gson().newBuilder().create();
	}

}
