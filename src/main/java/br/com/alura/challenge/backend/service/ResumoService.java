package br.com.alura.challenge.backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.dto.CategoriaTotalMesDto;
import br.com.alura.challenge.backend.model.Categoria;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import br.com.alura.challenge.backend.repository.ResumoRepository;

@Service
public class ResumoService {
	
	
	ResumoRepository resumoRepository;
	CategoriaRepository categoriaRepository;
	
	@Autowired
	ResumoService(ResumoRepository resumoRepository, CategoriaRepository categoriaRepository){
		this.resumoRepository = resumoRepository;
		this.categoriaRepository = categoriaRepository;
	}
	
	public List<CategoriaTotalMesDto> relatorioCategorias(int ano, int mes){
		
		List<Categoria> categorias = categoriaRepository.findAll();
		
		List<CategoriaTotalMesDto> valoresCategorias = new ArrayList<>(8);
		
		categorias.stream().forEach(e -> {		
			BigDecimal valorCategoria = resumoRepository.buscarValorTotalCategoria(e.getId(), ano, mes);
			valoresCategorias.add(new CategoriaTotalMesDto(e.getDescricao(), valorCategoria));
		});
		
		return valoresCategorias;
		
	}

}
