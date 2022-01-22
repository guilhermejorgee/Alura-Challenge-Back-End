package br.com.alura.challenge.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.backend.domain.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	Optional<Despesa> findByDescricao(String descricao);

}
