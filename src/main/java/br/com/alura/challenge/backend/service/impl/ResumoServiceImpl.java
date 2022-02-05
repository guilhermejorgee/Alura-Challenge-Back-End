package br.com.alura.challenge.backend.service.impl;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.dto.ResumoDto;
import br.com.alura.challenge.backend.repository.DespesaRepository;
import br.com.alura.challenge.backend.repository.ReceitaRepository;
import br.com.alura.challenge.backend.service.ResumoService;

@Service
@Transactional
public class ResumoServiceImpl implements ResumoService {
	
	DespesaRepository despesaRepository;
	ReceitaRepository receitaRepository;
	
	@Autowired
	ResumoServiceImpl(DespesaRepository despesaRepository, ReceitaRepository receitaRepository){
		this.receitaRepository = receitaRepository;
		this.despesaRepository = despesaRepository;
	}
	
	@Override
	public ResumoDto gerarResumoMes(int ano, int mes) {		
		
		BigDecimal receita = receitaRepository.findByTotalReceita(ano, mes);		
		BigDecimal despesa = despesaRepository.findByTotalDespesa(ano, mes);	
		var valorCategorias = despesaRepository.buscarTotalValorCategoria(ano, mes);
		
		return new ResumoDto(receita, despesa, valorCategorias);
		
	}

}
