package br.com.alura.challenge.backend.config.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionHandlerDto {
	
	private int status;
	private String message;

}
