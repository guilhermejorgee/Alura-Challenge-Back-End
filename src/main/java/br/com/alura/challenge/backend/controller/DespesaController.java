package br.com.alura.challenge.backend.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alura.challenge.backend.dto.DespesaDto;
import br.com.alura.challenge.backend.dto.ReceitaDto;
import br.com.alura.challenge.backend.form.AtualizacaoDespesaForm;
import br.com.alura.challenge.backend.form.DespesaForm;
import br.com.alura.challenge.backend.model.Despesa;
import br.com.alura.challenge.backend.model.Receita;
import br.com.alura.challenge.backend.repository.CategoriaRepository;
import br.com.alura.challenge.backend.repository.DespesaRepository;
import br.com.alura.challenge.backend.service.DespesaService;

@RestController
@RequestMapping("/despesas")
@Transactional
public class DespesaController {
	
	DespesaRepository despesaRepository;
	CategoriaRepository categoriaRepository;
	DespesaService despesaService;
	
	@Autowired
	DespesaController(DespesaRepository despesaRepository, CategoriaRepository categoriaRepository, DespesaService despesaService){
		this.despesaRepository = despesaRepository;
		this.categoriaRepository = categoriaRepository;
		this.despesaService = despesaService;
	}
	
	@PostMapping
	public ResponseEntity<DespesaDto> cadastro(@RequestBody @Valid DespesaForm form){
		
		if(despesaService.checarDuplicidade(form.getDescricao())){
			Despesa despesa = form.converter(despesaService);
			despesaRepository.save(despesa);
			URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/despesa/{id}").buildAndExpand(despesa.getId()).toUriString());
			return ResponseEntity.created(uri).body(new DespesaDto(despesa));
		}
		
		return ResponseEntity.badRequest().build();	

	}
	
	@GetMapping
	public ResponseEntity<List<DespesaDto>> listagem(){
		List<Despesa> despesa = despesaRepository.findAll();
		return ResponseEntity.ok(DespesaDto.converter(despesa));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaDto> detalhamentoDespesa(@PathVariable Long id){
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if(despesa.isPresent()) {
			return ResponseEntity.ok(new DespesaDto(despesa.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DespesaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoDespesaForm form){
		Optional<Despesa> despesa = despesaRepository.findById(id);
		
		if(despesa.isPresent()) {
			if(despesaService.checarDuplicidade(form.getDescricao())){
				despesa.get().atualizar(form);
				return ResponseEntity.ok(new DespesaDto(despesa.get()));
			}else {
				return ResponseEntity.badRequest().build();
			}
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Despesa> despesa = despesaRepository.findById(id);
		
		if(despesa.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(params="descricao")
	public ResponseEntity<List<DespesaDto>> buscarReceitaPorDescricao(@RequestParam(value="descricao") String descricao){
		List<Despesa> despesas = despesaRepository.findByDescricaoContainsIgnoreCase(descricao);
		return ResponseEntity.ok(DespesaDto.converter(despesas));
		
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<DespesaDto>> buscarDespesasPorMesEAno(@PathVariable int ano, @PathVariable int mes){
		return ResponseEntity.ok(DespesaDto.converter(despesaRepository.findByYearAndMonth(ano, mes)));		
	}
	
	
}
