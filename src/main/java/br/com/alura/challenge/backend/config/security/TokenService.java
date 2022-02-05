package br.com.alura.challenge.backend.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.alura.challenge.backend.model.Usuario;
import br.com.alura.challenge.backend.repository.UsuarioRepository;

@Service
public class TokenService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
	
	public String gerar(Authentication authentication) {
		
		Usuario logado = (Usuario) authentication.getPrincipal();
					
		return JWT.create()
				.withIssuer("API Challenge Backend Alura")
				.withSubject(logado.getId().toString())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.sign(algorithm);
				
		}

	public boolean isTokenValido(String token) {
		
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret".getBytes())).build();
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Usuario getUsuario(String token) {	
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret".getBytes())).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		return usuarioRepository.findById(Long.valueOf(decodedJWT.getSubject())).get();
	}
		

}
