package br.com.alura.challenge.backend.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.challenge.backend.model.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaForm {
	
	@NotNull @NotEmpty
	private String descricao;
	
	@NotNull
	private BigDecimal valor;
	
	ReceitaForm(Receita receita){
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
	}

	public Receita converter() {
		return new Receita(this.getDescricao(), this.getValor());
	}

}
