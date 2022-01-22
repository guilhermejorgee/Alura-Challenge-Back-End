package br.com.alura.challenge.backend.controller.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AtualizacaoDespesaForm {
	
	@NotNull @NotEmpty
	private String descricao;
	@NotNull
	private BigDecimal valor;

}
