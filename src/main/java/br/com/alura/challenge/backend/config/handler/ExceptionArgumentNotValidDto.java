package br.com.alura.challenge.backend.config.handler;

import org.springframework.validation.FieldError;

import lombok.Getter;

@Getter
public class ExceptionArgumentNotValidDto {
	
	String field;
	String message;
	
	public ExceptionArgumentNotValidDto(FieldError element) {
		this.field = element.getField();
		this.message = element.getDefaultMessage();
	}
	

}
