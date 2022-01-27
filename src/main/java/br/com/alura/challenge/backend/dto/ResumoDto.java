package br.com.alura.challenge.backend.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public class ResumoDto {
	
	BigDecimal receitaTotal;
	BigDecimal despesaTotal;
	BigDecimal saldoFinal;	
	
	List<CategoriaTotalMesDto> categorias;
	
	public ResumoDto(BigDecimal receitaTotal, BigDecimal despesaTotal, List<CategoriaTotalMesDto> listaCategorias){
		if(Optional.ofNullable(receitaTotal).isEmpty()) {
			receitaTotal = new BigDecimal(0);
		}
		if(Optional.ofNullable(despesaTotal).isEmpty()) {
			despesaTotal = new BigDecimal(0);
		}
		this.receitaTotal = receitaTotal;
		this.despesaTotal = despesaTotal;
		this.saldoFinal = receitaTotal.subtract(despesaTotal);
		this.categorias = listaCategorias;
	}

}
