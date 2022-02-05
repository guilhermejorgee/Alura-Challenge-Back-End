package br.com.alura.challenge.backend.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.challenge.backend.model.Usuario;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;

	public AutenticacaoTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperarToken(request);
		
		boolean tokenValido = tokenService.isTokenValido(token);
		
		if(tokenValido) {
			autenticarUsuario(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private void autenticarUsuario(String token) {
		
		Usuario usuario = tokenService.getUsuario(token);
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

	private String recuperarToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7);
		
	}

}
