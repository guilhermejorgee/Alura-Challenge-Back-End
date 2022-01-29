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

import br.com.alura.challenge.backend.dto.DespesaDto;
import br.com.alura.challenge.backend.form.AtualizacaoDespesaForm;
import br.com.alura.challenge.backend.form.DespesaForm;
import br.com.alura.challenge.backend.service.DespesaService;

@RestController
@RequestMapping("/despesas")
public class DespesaController {
	
	@Autowired
	DespesaService despesaService;
	
	
	@PostMapping
	public ResponseEntity<DespesaDto> cadastro(@RequestBody @Valid DespesaForm form){	
		
		DespesaDto despesa = despesaService.cadastrarDespesa(form);
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/despesa/{id}").buildAndExpand(despesa.getId()).toUriString());
		
		return ResponseEntity.created(uri).body(despesa);
	}
	
	@GetMapping
	public ResponseEntity<List<DespesaDto>> listagem(){
		return ResponseEntity.ok(despesaService.listarTodasDespesas());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaDto> detalhamentoDespesa(@PathVariable Long id){		
		return ResponseEntity.ok(despesaService.despesaDetalhada(id));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DespesaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoDespesaForm form){	
		return ResponseEntity.ok(despesaService.atualizarDespesa(id, form));	
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id){
		despesaService.removerDespesa(id);
	}
	
	@GetMapping(params="descricao")
	public ResponseEntity<List<DespesaDto>> despesaPorDescricao(String descricao){
		return ResponseEntity.ok(despesaService.buscarDespesaPorDescricao(descricao));
		
	}
	
	@GetMapping("/{ano}/{mes}")
	public ResponseEntity<List<DespesaDto>> despesasPorMesEAno(@PathVariable int ano, @PathVariable int mes){
		return ResponseEntity.ok(despesaService.buscarDespesasPorMesEAno(ano, mes));		
	}
	
	
}
