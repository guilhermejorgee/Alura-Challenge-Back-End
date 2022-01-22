package br.com.alura.challenge.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.domain.Despesa;
import br.com.alura.challenge.backend.repository.DespesaRepository;

@Service
public class DespesaService {
	
	@Autowired
	DespesaRepository despesaRepository;
	
	public boolean checarDuplicidade(String descricao) {
		Optional<Despesa> despesa = despesaRepository.findByDescricao(descricao);
		
		if(despesa.isPresent()) {
			LocalDateTime dataAgora = LocalDateTime.now();
			dataAgora.getMonth();
			despesa.get().getData().getMonth();
			if(dataAgora.getMonth().equals(despesa.get().getData().getMonth())){
				return false;
			}else {
				return true;
			}
		}
		
		return true;
	}
	
}
