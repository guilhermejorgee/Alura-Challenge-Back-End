package br.com.alura.challenge.backend.service;

import br.com.alura.challenge.backend.dto.ResumoDto;

public interface ResumoService {
	
	ResumoDto gerarResumoMes(int ano, int mes);

}
