package br.com.alura.challenge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.backend.domain.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	Optional<Receita> findByDescricao(String descricao);
	

}
