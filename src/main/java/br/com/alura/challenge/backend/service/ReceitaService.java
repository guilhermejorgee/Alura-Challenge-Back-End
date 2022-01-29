package br.com.alura.challenge.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.dto.ReceitaDto;
import br.com.alura.challenge.backend.exception.InformacaoComDuplicidadeException;
import br.com.alura.challenge.backend.form.ReceitaForm;
import br.com.alura.challenge.backend.model.Receita;
import br.com.alura.challenge.backend.repository.ReceitaRepository;

@Service
@Transactional
public class ReceitaService {
	
	@Autowired
	ReceitaRepository receitaRepository;
	
	public List<ReceitaDto> buscarReceitaPorMesEAno(int ano, int mes){
		return ReceitaDto.converter(receitaRepository.findByYearAndMonth(ano, mes));	
	}
	
	public List<ReceitaDto> buscarReceitaPorDescricao(String descricao){		
		return ReceitaDto.converter(receitaRepository.findByDescricaoContainsIgnoreCase(descricao));	
	}
	
	public boolean removerReceita(Long id) {
		
		Optional<Receita> receita = receitaRepository.findById(id);
		if(receita.isPresent()) {
			receitaRepository.deleteById(id);
			return true;
		}
		
		return false;
		
	}
	
	public Optional<ReceitaDto> atualizarReceita(Long id, ReceitaForm form){
		
		Optional<Receita> receita = receitaRepository.findById(id);
		
		if(receita.isPresent()) {
			if(checarDuplicidade(form.getDescricao(), id)){
				receita.get().atualizar(form);
				return Optional.of(new ReceitaDto(receita.get()));
			}else {
				throw new InformacaoComDuplicidadeException("Já há uma receita com as mesmas caracteristicas no banco de dados");
			}
		}
		
		return Optional.empty();
	}
	
	public Optional<ReceitaDto> ReceitaDetalhada(Long id) {
		
		Optional<Receita> receita = receitaRepository.findById(id);
		
		if(receita.isPresent()) {
			return Optional.of(new ReceitaDto(receita.get()));
		}
		return Optional.empty();
		
	}
	
	public List<ReceitaDto> listarTodasReceitas(){
		return ReceitaDto.converter(receitaRepository.findAll());
	}
	
	public Optional<ReceitaDto> cadastrarReceita(ReceitaForm form) {
		
		if(checarDuplicidade(form.getDescricao(), null)){
			Receita receita = form.converter();
			return Optional.of(new ReceitaDto(receitaRepository.save(receita)));
		}
		
		return Optional.empty();
		
	}

	public boolean checarDuplicidade(String descricao, Long id) {
	
		Optional<Receita> receita = null;
		
		if(id == null) {
			receita = receitaRepository.findByDescricaoIgnoreCase(descricao);
		}else {
			receita = receitaRepository.findByDescricaoExcecaoId(descricao, id);
		}

		
		if(receita.isPresent()) {
			LocalDateTime dataAgora = LocalDateTime.now();
			if(dataAgora.getMonth().equals(receita.get().getData().getMonth())){
				return false;
			}else {
				return true;
			}
		}
		
		return true;
	}
	

}
