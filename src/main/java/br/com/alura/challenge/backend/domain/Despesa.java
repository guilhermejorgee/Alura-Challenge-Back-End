package br.com.alura.challenge.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.alura.challenge.backend.controller.form.AtualizacaoDespesaForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Despesa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private LocalDateTime data = LocalDateTime.now();
	
	@ManyToOne
	private Categoria categoria;
	
	public Despesa(String descricao, BigDecimal valor, Categoria categoria) {
		this.descricao = descricao;
		this.valor = valor;
		this.categoria = categoria;
	}

	public void atualizar(AtualizacaoDespesaForm form) {
		setDescricao(form.getDescricao());
		setValor(form.getValor());
	}

}
