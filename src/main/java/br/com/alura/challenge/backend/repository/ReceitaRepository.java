package br.com.alura.challenge.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.challenge.backend.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

	Optional<Receita> findByDescricaoIgnoreCase(String descricao);
	
	List<Receita> findByDescricaoContainsIgnoreCase(String descricao);
	
	@Query("select e from Receita e where year(e.data) = ?1 and month(e.data) = ?2")
	List<Receita> findByYearAndMonth(int ano, int mes);

}
