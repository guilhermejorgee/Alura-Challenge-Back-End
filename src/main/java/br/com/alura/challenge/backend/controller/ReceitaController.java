package br.com.alura.challenge.backend.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alura.challenge.backend.controller.dto.ReceitaDto;
import br.com.alura.challenge.backend.controller.form.ReceitaForm;
import br.com.alura.challenge.backend.domain.Receita;
import br.com.alura.challenge.backend.repository.ReceitaRepository;
import br.com.alura.challenge.backend.service.ReceitaService;

@RestController
@RequestMapping("/receita")
@Transactional
public class ReceitaController {
	
	ReceitaRepository receitaRepository;
	ReceitaService receitaService;
	
	@Autowired
	ReceitaController(ReceitaRepository receitaRepository, ReceitaService receitaService){
		this.receitaRepository = receitaRepository;
		this.receitaService = receitaService;
	}
	
	@PostMapping
	public ResponseEntity<ReceitaDto> cadastrar(@RequestBody @Valid ReceitaForm form){
		
		if(receitaService.checarDuplicidade(form.getDescricao())) {
			Receita receita = form.converter();
			receitaRepository.save(receita);
			URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/receita/{id}").buildAndExpand(receita.getId()).toUriString());
			return ResponseEntity.created(uri).body(new ReceitaDto(receita));
		}
		
		return ResponseEntity.badRequest().build();
	
	}
	
	@GetMapping
	public ResponseEntity<List<ReceitaDto>> listagem(){
		List<Receita> receitas = receitaRepository.findAll();
		return ResponseEntity.ok(ReceitaDto.converter(receitas));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDto> detalhamentoReceita(@PathVariable Long id){
		Optional<Receita> receita = receitaRepository.findById(id);
		if(receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDto(receita.get()));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDto> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaForm form){
		Optional<Receita> receita = receitaRepository.findById(id);
		if(receita.isPresent()) {
			if(receitaService.checarDuplicidade(form.getDescricao())) {
				receita.get().atualizar(form);
				return ResponseEntity.ok(new ReceitaDto(receita.get()));
			}else {
				return ResponseEntity.badRequest().build();
			}
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id){
		Optional<Receita> receita = receitaRepository.findById(id);
		if(receita.isPresent()) {
			receitaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	

}
