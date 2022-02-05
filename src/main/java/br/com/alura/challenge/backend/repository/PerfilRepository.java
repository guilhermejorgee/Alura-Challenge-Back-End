package br.com.alura.challenge.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.challenge.backend.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
