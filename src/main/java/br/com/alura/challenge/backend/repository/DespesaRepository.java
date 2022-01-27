package br.com.alura.challenge.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.challenge.backend.model.Despesa;
import br.com.alura.challenge.backend.model.Receita;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	Optional<Despesa> findByDescricaoIgnoreCase(String descricao);
	
	List<Despesa> findByDescricaoContainsIgnoreCase(String descricao);
	
	@Query("select e from Despesa e where year(e.data) = ?1 and month(e.data) = ?2")
	List<Despesa> findByYearAndMonth(int ano, int mes);
	
}
