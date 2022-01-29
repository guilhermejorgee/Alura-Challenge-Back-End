package br.com.alura.challenge.backend.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.dto.ResumoDto;
import br.com.alura.challenge.backend.repository.DespesaRepository;
import br.com.alura.challenge.backend.repository.ReceitaRepository;

@Service
@Transactional
public class ResumoService {
	
	DespesaRepository despesaRepository;
	ReceitaRepository receitaRepository;
	
	@Autowired
	ResumoService(DespesaRepository despesaRepository, ReceitaRepository receitaRepository){
		this.receitaRepository = receitaRepository;
		this.despesaRepository = despesaRepository;
	}
	
	public ResumoDto gerarResumoMes(int ano, int mes) {
		
		BigDecimal receita = receitaRepository.findByTotalReceita(ano, mes);
		
		BigDecimal despesa = despesaRepository.findByTotalDespesa(ano, mes);
		
		var valorCategorias = despesaRepository.buscarTotalValorCategoria(ano, mes);
		
		return new ResumoDto(receita, despesa, valorCategorias);
		
	}

}
