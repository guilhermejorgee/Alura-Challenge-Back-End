package br.com.alura.challenge.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;

@Getter
public class ResumoDto {
	
	BigDecimal receitaTotal;
	BigDecimal despesaTotal;
	BigDecimal saldoFinal;	
	
	List<CategoriaTotalMesDto> categorias;
	
	public ResumoDto(List<BigDecimal> lista, List<CategoriaTotalMesDto> listaCategorias){
		this.receitaTotal = lista.get(0);
		this.despesaTotal = lista.get(1);
		this.saldoFinal = lista.get(2);
		this.categorias = listaCategorias;
	}

}
