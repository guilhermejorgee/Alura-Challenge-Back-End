package br.com.alura.challenge.backend.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.alura.challenge.backend.domain.Despesa;
import lombok.Getter;

@Getter
public class DespesaDto {
	
	private Long id;
	private String descricao;
	private BigDecimal valor;
	private String categoria;
	private LocalDateTime data;
	
	public DespesaDto(Despesa despesa){
		this.id = despesa.getId();
		this.descricao = despesa.getDescricao();
		this.valor = despesa.getValor();
		this.categoria = despesa.getCategoria().getDescricao();
		this.data = despesa.getData();
	}

	public static List<DespesaDto> converter(List<Despesa> despesa) {
		return despesa.stream().map(element -> new DespesaDto(element)).toList();
	}

}
