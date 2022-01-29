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
	
	public List<DespesaDto> buscarDespesasPorMesEAno(int ano, int mes){
		return DespesaDto.converter(despesaRepository.findByYearAndMonth(ano, mes));	
	}
	
	public List<DespesaDto> buscarDespesaPorDescricao(String descricao){
		List<Despesa> despesas = despesaRepository.findByDescricaoContainsIgnoreCase(descricao);
		return DespesaDto.converter(despesas);
	}
	
	public boolean removerDespesa(Long id){
		
		Optional<Despesa> despesa = despesaRepository.findById(id);
		
		if(despesa.isPresent()) {
			despesaRepository.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	public Optional<DespesaDto> atualizarDespesa(Long id, AtualizacaoDespesaForm form){
		
		Optional<Despesa> despesa = despesaRepository.findById(id);
		
		if(despesa.isPresent()) {
			if(checarDuplicidade(form.getDescricao(), id)){
				despesa.get().atualizar(form);
				return Optional.of(new DespesaDto(despesa.get()));
			}else {
				throw new InformacaoComDuplicidadeException("Já há uma despesa com as mesmas caracteristicas no banco de dados");
			}
		}
		
		return Optional.empty();
	}
	
	
	public Optional<DespesaDto> despesaDetalhada(Long id) {
		
		Optional<Despesa> despesa = despesaRepository.findById(id);
		
		if(despesa.isPresent()) {
			return Optional.of(new DespesaDto(despesa.get()));
		}
		return Optional.empty();
		
	}
	
	public List<DespesaDto> listarTodasDespesas(){
		return DespesaDto.converter(despesaRepository.findAll());
	}
	
	public Optional<DespesaDto> cadastrarDespesa(DespesaForm form) {
		
		if(checarDuplicidade(form.getDescricao(), null)){
			Despesa despesa = form.converter(checarCategoria(form.getNomeCategoria()));
			return Optional.of(new DespesaDto(despesaRepository.save(despesa)));
		}
		
		return Optional.empty();
		
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
