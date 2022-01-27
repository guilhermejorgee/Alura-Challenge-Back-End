package br.com.alura.challenge.backend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.exception.CategoriaInvalidaException;
import br.com.alura.challenge.backend.model.Categoria;
import br.com.alura.challenge.backend.model.Despesa;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import br.com.alura.challenge.backend.repository.DespesaRepository;

@Service
public class DespesaService {
	
	@Autowired
	DespesaRepository despesaRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	public boolean checarDuplicidade(String descricao) {
		Optional<Despesa> despesa = despesaRepository.findByDescricaoIgnoreCase(descricao);
		
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

	public Categoria checarCategoria(String nomeCategoria){
		
		if(nomeCategoria != null) {
			
			Optional<Categoria> categoria = categoriaRepository.findByDescricaoIgnoreCase(nomeCategoria);
			
			if(categoria.isPresent()) {
				return categoria.get();
			}
			else {
				throw new CategoriaInvalidaException("Inválido, deixe vazio ou informe uma categoria válida");
			}
			
		}
		
		return categoriaRepository.findByDescricaoIgnoreCase("Outras").get(); 

	}
	
}
