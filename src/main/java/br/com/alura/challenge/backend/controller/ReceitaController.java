package br.com.alura.challenge.backend.controller;

import java.net.URI;
import java.util.List;

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
import br.com.alura.challenge.backend.form.ReceitaForm;
import br.com.alura.challenge.backend.service.ReceitaService;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {
	
	@Autowired
	ReceitaService receitaService;
	
	@PostMapping
	public ResponseEntity<ReceitaDto> cadastrar(@RequestBody @Valid ReceitaForm form){
		
		ReceitaDto receita = receitaService.cadastrarReceita(form);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/receita/{id}").buildAndExpand(receita.getId()).toUriString());
		
		return ResponseEntity.created(uri).body(receita);
			
	}
	
	@GetMapping
	public ResponseEntity<List<ReceitaDto>> listagem(){
		return ResponseEntity.ok(receitaService.listarTodasReceitas());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDto> detalhamentoReceita(@PathVariable Long id){	
		return ResponseEntity.ok(receitaService.receitaDetalhada(id));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDto> atualizar(@PathVariable Long id, @RequestBody @Valid ReceitaForm form){	
		return ResponseEntity.ok(receitaService.atualizarReceita(id, form));	
		
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id){
		receitaService.removerReceita(id);	
	}
	
	@GetMapping(params="descricao")
	public ResponseEntity<List<ReceitaDto>> receitaPorDescricao(String descricao){
		return ResponseEntity.ok(receitaService.buscarReceitasPorDescricao(descricao));
		
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<ReceitaDto>> receitaPorMesEAno(@PathVariable int ano, @PathVariable int mes){
		return ResponseEntity.ok(receitaService.buscarReceitasPorMesEAno(ano, mes));	
	}
	
}
