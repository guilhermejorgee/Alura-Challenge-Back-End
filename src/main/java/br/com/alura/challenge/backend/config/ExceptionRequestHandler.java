package br.com.alura.challenge.backend.config;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.challenge.backend.dto.ExceptionArgumentNotValidDto;
import br.com.alura.challenge.backend.dto.ExceptionHandlerDto;
import br.com.alura.challenge.backend.exception.CategoriaInvalidaException;
import br.com.alura.challenge.backend.exception.InformacaoComDuplicidadeException;
import br.com.alura.challenge.backend.exception.InformacaoNaoEncontradaException;

@RestControllerAdvice
public class ExceptionRequestHandler {
	
	
	@ExceptionHandler(value = {CategoriaInvalidaException.class, InformacaoComDuplicidadeException.class})
	public ResponseEntity<ExceptionHandlerDto> argumentNotValid(Exception ex){	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionHandlerDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ExceptionArgumentNotValidDto>> argumentNotValid(MethodArgumentNotValidException ex){
		List<ExceptionArgumentNotValidDto> listaErros = ex.getFieldErrors().stream().map(ExceptionArgumentNotValidDto::new).toList();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(listaErros);

	}
	
	@ExceptionHandler(InformacaoNaoEncontradaException.class)
	public ResponseEntity<ExceptionHandlerDto> attributeNotFound(InformacaoNaoEncontradaException ex){	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionHandlerDto(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
	}

}
