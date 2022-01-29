package br.com.alura.challenge.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionHandlerDto {
	
	private int status;
	private String message;

}
