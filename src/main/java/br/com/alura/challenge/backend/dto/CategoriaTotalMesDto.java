package br.com.alura.challenge.backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoriaTotalMesDto {
	
	String descricao;
	BigDecimal total;

}
