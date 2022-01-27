package br.com.alura.challenge.backend.exception;

public class InformacaoNaoEncontradaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976616555409741216L;
	
	public InformacaoNaoEncontradaException(String message){
		super(message);
	}

}
