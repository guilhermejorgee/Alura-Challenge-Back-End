package br.com.alura.challenge.backend.config.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.challenge.backend.dto.LoginForm;
import br.com.alura.challenge.backend.dto.TokenDto;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginForm form){
		
		try {
			Authentication authentication = authManager.authenticate(form.converter());
			String token = tokenService.gerar(authentication);
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			ResponseEntity.badRequest().build();
		}	
		
		return null;
	}
	
}
