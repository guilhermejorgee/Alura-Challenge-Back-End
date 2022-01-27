package br.com.alura.challenge.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.model.Receita;
import br.com.alura.challenge.backend.repository.ReceitaRepository;

@Service
public class ReceitaService {
	
	@Autowired
	ReceitaRepository receitaRepository;

	public boolean checarDuplicidade(String descricao) {
		Optional<Receita> receita = receitaRepository.findByDescricaoIgnoreCase(descricao);
		
		if(receita.isPresent()) {
			LocalDateTime dataAgora = LocalDateTime.now();
			dataAgora.getMonth();
			receita.get().getData().getMonth();
			if(dataAgora.getMonth().equals(receita.get().getData().getMonth())){
				return false;
			}else {
				return true;
			}
		}
		
		return true;
	}
	

}
