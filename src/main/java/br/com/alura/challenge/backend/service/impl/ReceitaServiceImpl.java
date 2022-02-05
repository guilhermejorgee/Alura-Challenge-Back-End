package br.com.alura.challenge.backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.dto.ReceitaDto;
import br.com.alura.challenge.backend.exception.InformacaoComDuplicidadeException;
import br.com.alura.challenge.backend.exception.InformacaoNaoEncontradaException;
import br.com.alura.challenge.backend.form.ReceitaForm;
import br.com.alura.challenge.backend.model.Receita;
import br.com.alura.challenge.backend.repository.ReceitaRepository;
import br.com.alura.challenge.backend.service.ReceitaService;

@Service
@Transactional
public class ReceitaServiceImpl implements ReceitaService {
	
	@Autowired
	ReceitaRepository receitaRepository;
	
	@Override
	public Receita consultarReceita(Long id) {
		return receitaRepository.findById(id).orElseThrow(() -> new InformacaoNaoEncontradaException("ID não encontrado"));
	}
	
	@Override
	public List<ReceitaDto> buscarReceitasPorMesEAno(int ano, int mes){
		return ReceitaDto.converter(receitaRepository.findByYearAndMonth(ano, mes));	
	}
	
	@Override
	public List<ReceitaDto> buscarReceitasPorDescricao(String descricao){		
		return ReceitaDto.converter(receitaRepository.findByDescricaoContainsIgnoreCase(descricao));	
	}
	
	@Override
	public void removerReceita(Long id) {
		consultarReceita(id);	
		receitaRepository.deleteById(id);	
	}
	
	@Override
	public ReceitaDto atualizarReceita(Long id, ReceitaForm form){
		
		Receita receita = consultarReceita(id);
		
			if(checarDuplicidade(form.getDescricao(), id)){
				receita.atualizar(form);
			}
			
		return new ReceitaDto(receita);	
	}
	
	@Override
	public ReceitaDto receitaDetalhada(Long id) {
		return new ReceitaDto(consultarReceita(id));
	}
	
	@Override
	public List<ReceitaDto> listarTodasReceitas(){
		return ReceitaDto.converter(receitaRepository.findAll());
	}
	
	@Override
	public ReceitaDto cadastrarReceita(ReceitaForm form) {
		
		Receita receita = null;
		
		if(checarDuplicidade(form.getDescricao(), null)){
			receita = form.converter();
		}
		
		return new ReceitaDto(receitaRepository.save(receita));	
	}
	
	@Override
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
				throw new InformacaoComDuplicidadeException("Já há uma receita com as mesmas caracteristicas no banco de dados");
			}else {
				return true;
			}
		}		
		return true;
	}
	
}
