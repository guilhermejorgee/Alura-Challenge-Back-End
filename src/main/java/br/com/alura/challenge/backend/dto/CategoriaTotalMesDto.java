package br.com.alura.challenge.backend.dto;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;

@Getter
public class CategoriaTotalMesDto {
	
	String descricao;
	BigDecimal total;
	
	public CategoriaTotalMesDto(String descricao, BigDecimal total){
		if(Optional.ofNullable(total).isEmpty()){
			total = new BigDecimal(0);
		}
		this.descricao = descricao;
		this.total = total;
	}
	

}
