package br.com.alura.challenge.backend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.backend.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByDescricaoIgnoreCase(String nomeCategoria);

}
