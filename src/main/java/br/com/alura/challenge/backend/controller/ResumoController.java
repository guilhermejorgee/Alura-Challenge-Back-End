package br.com.alura.challenge.backend.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.challenge.backend.dto.ResumoDto;
import br.com.alura.challenge.backend.repository.DespesaRepository;
import br.com.alura.challenge.backend.repository.ReceitaRepository;

@RestController
@RequestMapping("/resumo")
@Transactional
public class ResumoController {
	
	DespesaRepository despesaRepository;
	ReceitaRepository receitaRepository;
	
	
	@Autowired
	ResumoController(DespesaRepository despesaRepository, ReceitaRepository receitaRepository){
		this.receitaRepository = receitaRepository;
		this.despesaRepository = despesaRepository;
	}
	
	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<ResumoDto> buscar(@PathVariable int ano, @PathVariable int mes){	
		
		BigDecimal receita = receitaRepository.findByTotalReceita(ano, mes);
		
		BigDecimal despesa = despesaRepository.findByTotalDespesa(ano, mes);
		
		var valorCategorias = despesaRepository.buscarTotalValorCategoria(ano, mes);
		
		return ResponseEntity.ok(new ResumoDto(receita, despesa, valorCategorias));
		
	}

}
