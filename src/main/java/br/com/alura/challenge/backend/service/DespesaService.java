package br.com.alura.challenge.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.challenge.backend.dto.DespesaDto;
import br.com.alura.challenge.backend.exception.CategoriaInvalidaException;
import br.com.alura.challenge.backend.exception.InformacaoComDuplicidadeException;
import br.com.alura.challenge.backend.exception.InformacaoNaoEncontradaException;
import br.com.alura.challenge.backend.form.AtualizacaoDespesaForm;
import br.com.alura.challenge.backend.form.DespesaForm;
import br.com.alura.challenge.backend.model.Categoria;
import br.com.alura.challenge.backend.model.Despesa;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import br.com.alura.challenge.backend.repository.DespesaRepository;

@Service
@Transactional
public class DespesaService {
	
	DespesaRepository despesaRepository;
	CategoriaRepository categoriaRepository;
	
	@Autowired
	public DespesaService(DespesaRepository despesaRepository, CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
		this.despesaRepository = despesaRepository;
	}
	
	public Despesa consultarDespesa(Long id) {
		return despesaRepository.findById(id).orElseThrow(() -> new InformacaoNaoEncontradaException("ID não encontrado"));
	}
	
	public List<DespesaDto> buscarDespesasPorMesEAno(int ano, int mes){
		return DespesaDto.converter(despesaRepository.findByYearAndMonth(ano, mes));	
	}
	
	public List<DespesaDto> buscarDespesaPorDescricao(String descricao){
		return DespesaDto.converter(despesaRepository.findByDescricaoContainsIgnoreCase(descricao));
	}
	
	public void removerDespesa(Long id){
		
		consultarDespesa(id);
		
		despesaRepository.deleteById(id);
	}
	
	
	public DespesaDto atualizarDespesa(Long id, AtualizacaoDespesaForm form){
		
		Despesa despesa = consultarDespesa(id);
		
		if(checarDuplicidade(form.getDescricao(), id)){
			despesa.atualizar(form);		
		}		
		return new DespesaDto(despesa);
	}
	
	
	public DespesaDto despesaDetalhada(Long id) {
		return new DespesaDto(consultarDespesa(id));
	}
	
	public List<DespesaDto> listarTodasDespesas(){
		return DespesaDto.converter(despesaRepository.findAll());
	}
	
	public DespesaDto cadastrarDespesa(DespesaForm form) {
		
		Despesa despesa = null;
		
		if(checarDuplicidade(form.getDescricao(), null)){
			despesa = form.converter(checarCategoria(form.getNomeCategoria()));
			
		}	
		
		return new DespesaDto(despesaRepository.save(despesa));
		
	}
	
	public boolean checarDuplicidade(String descricao, Long id) {
		
		Optional<Despesa> despesa = null;
		
		if(id == null) {
			despesa = despesaRepository.findByDescricaoIgnoreCase(descricao);
		}else {
			despesa = despesaRepository.findByDescricaoExcecaoId(descricao, id);
		}

		
		if(despesa.isPresent()) {
			LocalDateTime dataAgora = LocalDateTime.now();
			if(dataAgora.getMonth().equals(despesa.get().getData().getMonth())){
				throw new InformacaoComDuplicidadeException("Já há uma despesa com as mesmas caracteristicas no banco de dados");
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
