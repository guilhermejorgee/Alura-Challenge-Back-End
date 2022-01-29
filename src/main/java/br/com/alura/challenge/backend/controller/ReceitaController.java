package br.com.alura.challenge.backend.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import br.com.alura.challenge.backend.dto.ReceitaDto;
import br.com.alura.challenge.backend.exception.InformacaoComDuplicidadeException;
import br.com.alura.challenge.backend.exception.InformacaoNaoEncontradaException;
import br.com.alura.challenge.backend.form.ReceitaForm;
import br.com.alura.challenge.backend.repository.ReceitaRepository;
import br.com.alura.challenge.backend.service.ReceitaService;

@RestController
@RequestMapping("/receitas")
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
		
		Optional<ReceitaDto> receita = receitaService.cadastrarReceita(form);
		
		if(receita.isEmpty()) {
			throw new InformacaoComDuplicidadeException("Já há uma receita com as mesmas caracteristicas no sistema"); 
		}
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/receita/{id}").buildAndExpand(receita.get().getId()).toUriString());
		return ResponseEntity.created(uri).body(receita.get());
			
	}
	
	@GetMapping
	public ResponseEntity<List<ReceitaDto>> listagem(){
		return ResponseEntity.ok(receitaService.listarTodasReceitas());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDto> detalhamentoReceita(@PathVariable Long id){
		Optional<ReceitaDto> receita = receitaService.ReceitaDetalhada(id);
		if(receita.isEmpty()) {
			throw new InformacaoNaoEncontradaException("ID não encontrado");
		}
		
		return ResponseEntity.ok(receita.get());
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDto> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaForm form){
		
		Optional<ReceitaDto> receita = receitaService.atualizarReceita(id, form);
		
		if(receita.isEmpty()) {
			throw new InformacaoNaoEncontradaException("ID não encontrado");
		}
		
		return ResponseEntity.ok(receita.get());	
		
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id){

		if(!receitaService.removerReceita(id)) {
			throw new InformacaoNaoEncontradaException("ID não encontrado");
		}
		
	}
	
	@GetMapping(params="descricao")
	public ResponseEntity<List<ReceitaDto>> receitaPorDescricao(String descricao){
		return ResponseEntity.ok(receitaService.buscarReceitaPorDescricao(descricao));
		
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<ReceitaDto>> receitaPorMesEAno(@PathVariable int ano, @PathVariable int mes){
		return ResponseEntity.ok(receitaService.buscarReceitaPorMesEAno(ano, mes));	
	}
	
}
