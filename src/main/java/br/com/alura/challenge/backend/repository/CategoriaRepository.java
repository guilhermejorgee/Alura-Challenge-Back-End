package br.com.alura.challenge.backend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.backend.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByDescricao(String nomeCategoria);

}
