package br.com.alura.challenge.backend.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.alura.challenge.backend.domain.Receita;
import lombok.Getter;

@Getter
public class ReceitaDto {
	
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private LocalDateTime data;
	
	public ReceitaDto(Receita receita){
		this.id = receita.getId();
		this.descricao = receita.getDescricao();
		this.valor = receita.getValor();
		this.data = receita.getData();
	}

	public static List<ReceitaDto> converter(List<Receita> receitas) {
		return receitas.stream().map(ReceitaDto::new).toList();
	}

}
