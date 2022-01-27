package br.com.alura.challenge.backend.exception;

public class CategoriaInvalidaException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4458297598369760649L;

	public CategoriaInvalidaException(String message){
		super(message);
	}
	
}
