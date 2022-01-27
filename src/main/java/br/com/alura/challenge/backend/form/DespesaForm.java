package br.com.alura.challenge.backend.form;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.challenge.backend.model.Categoria;
import br.com.alura.challenge.backend.model.Despesa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DespesaForm {
	
	@NotNull @NotEmpty
	private String descricao;
	@NotNull
	private BigDecimal valor;

	private String nomeCategoria;
	
	public Despesa converter(Categoria categoria){
		return new Despesa(this.descricao, this.valor, categoria);
	}
	

}
