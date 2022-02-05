package br.com.alura.challenge.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.challenge.backend.dto.ResumoDto;
import br.com.alura.challenge.backend.service.ResumoService;

@RestController
@RequestMapping("/resumo")
public class ResumoController {
	
	@Autowired
	ResumoService resumoService;
	
	
	@GetMapping("/resumo/{ano}/{mes}")
	public ResponseEntity<ResumoDto> resumoMes(@PathVariable int ano, @PathVariable int mes){			
		return ResponseEntity.ok(resumoService.gerarResumoMes(ano, mes));	
	}	

}
