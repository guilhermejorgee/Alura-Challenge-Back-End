package br.com.alura.challenge.backend.form;

import java.math.BigDecimal;
import java.util.Optional;

import javax.management.AttributeNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.challenge.backend.model.Categoria;
import br.com.alura.challenge.backend.model.Despesa;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import br.com.alura.challenge.backend.service.DespesaService;
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
	
	public Despesa converter(DespesaService despesaService){
		return new Despesa(this.descricao, this.valor, despesaService.checarCategoria(this.nomeCategoria));
	}
	

}
