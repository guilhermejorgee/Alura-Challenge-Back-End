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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.alura.challenge.backend.dto.CategoriaTotalMesDto;
import br.com.alura.challenge.backend.dto.DespesaDto;
import br.com.alura.challenge.backend.exception.InformacaoComDuplicidadeException;
import br.com.alura.challenge.backend.exception.InformacaoNaoEncontradaException;
import br.com.alura.challenge.backend.form.AtualizacaoDespesaForm;
import br.com.alura.challenge.backend.form.DespesaForm;
import br.com.alura.challenge.backend.model.Despesa;
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
		
		Optional<DespesaDto> despesa = despesaService.cadastrarDespesa(form);
		
		if(despesa.isEmpty()) {
			throw new InformacaoComDuplicidadeException("Já há uma despesa com as mesmas caracteristicas no sistema"); 
		}
		
		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/despesa/{id}").buildAndExpand(despesa.get().getId()).toUriString());
		return ResponseEntity.created(uri).body(despesa.get());

	}
	
	@GetMapping
	public ResponseEntity<List<DespesaDto>> listagem(){
		return ResponseEntity.ok(despesaService.listarTodasDespesas());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaDto> detalhamentoDespesa(@PathVariable Long id){
		
		Optional<DespesaDto> despesa = despesaService.despesaDetalhada(id);
		
		if(despesa.isEmpty()) {	
			throw new InformacaoNaoEncontradaException("ID não encontrado");
		}
		
		return ResponseEntity.ok(despesa.get());
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DespesaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoDespesaForm form){
		
		Optional<DespesaDto> despesa = despesaService.atualizarDespesa(id, form);
		
		if(despesa.isEmpty()) {
			throw new InformacaoNaoEncontradaException("ID não encontrado");
		}		
		
		return ResponseEntity.ok(despesa.get());	
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
	
	@GetMapping("/categoria/{ano}/{mes}")
	public ResponseEntity<List<CategoriaTotalMesDto>> categoria(@PathVariable int ano, @PathVariable int mes){
		return ResponseEntity.ok(despesaRepository.buscarTotalValorCategoria(ano, mes));
	}
	
	
}
