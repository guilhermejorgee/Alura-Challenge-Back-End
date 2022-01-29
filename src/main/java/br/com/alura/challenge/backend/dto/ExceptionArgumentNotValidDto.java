package br.com.alura.challenge.backend.dto;

import org.springframework.validation.FieldError;

import lombok.Getter;

@Getter
public class ExceptionArgumentNotValidDto {
	
	private String field;
	private String message;
	
	public ExceptionArgumentNotValidDto(FieldError obj) {
		this.field = obj.getField();
		this.message = obj.getDefaultMessage();
	}
	

}
