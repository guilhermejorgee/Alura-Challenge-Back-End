package br.com.alura.challenge.backend.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AtualizacaoDespesaForm {
	
	@NotNull @NotEmpty
	private String descricao;
	@NotNull
	private BigDecimal valor;

}
