package br.com.alura.challenge.backend.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.alura.challenge.backend.form.ReceitaForm;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Receita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private LocalDateTime data = LocalDateTime.now();
	
	public Receita(String descricao, BigDecimal valor){
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public void atualizar(ReceitaForm form) {
		setDescricao(form.getDescricao());
		setValor(form.getValor());
	}
	
}
