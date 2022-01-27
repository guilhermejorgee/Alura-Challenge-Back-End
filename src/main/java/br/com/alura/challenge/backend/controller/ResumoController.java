package br.com.alura.challenge.backend.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.challenge.backend.dto.ResumoDto;
import br.com.alura.challenge.backend.repository.ResumoRepository;
import br.com.alura.challenge.backend.service.ResumoService;

@RestController
@RequestMapping("/resumo")
@Transactional
public class ResumoController {
	
	
	ResumoRepository resumoRepository;
	ResumoService resumoService;
	
	@Autowired
	ResumoController(ResumoRepository resumoRepository, ResumoService resumoService){
		this.resumoRepository = resumoRepository;
		this.resumoService = resumoService;
	}
	
	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<ResumoDto> buscar(@PathVariable int ano, @PathVariable int mes){	
		return ResponseEntity.ok(new ResumoDto(resumoRepository.relatorioSaldoMes(ano, mes), resumoService.relatorioCategorias(ano, mes)));
		
	}

}
