package br.com.alura.challenge.backend.controller.form;

import java.math.BigDecimal;
import java.util.Optional;

import javax.management.AttributeNotFoundException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.challenge.backend.domain.Categoria;
import br.com.alura.challenge.backend.domain.Despesa;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DespesaForm {
	
	@NotNull @NotEmpty
	private String descricao;
	@NotNull
	private BigDecimal valor;
	@NotNull @NotEmpty
	private String nomeCategoria;
	
	public Despesa converter(CategoriaRepository categoriaRepository) throws AttributeNotFoundException {
		
		Optional<Categoria> categoria = categoriaRepository.findByDescricao(this.nomeCategoria);
		
		if(categoria.isEmpty()) {
			throw new AttributeNotFoundException("Categoria não cadastrada no sistema");
		};
		
		return new Despesa(this.descricao, this.valor, categoria.get());
	}
	

}
