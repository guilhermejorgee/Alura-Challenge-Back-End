package br.com.alura.challenge.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.challenge.backend.dto.CategoriaTotalMesDto;
import br.com.alura.challenge.backend.model.Despesa;
import br.com.alura.challenge.backend.model.Receita;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

	Optional<Despesa> findByDescricaoIgnoreCase(String descricao);
	
	List<Despesa> findByDescricaoContainsIgnoreCase(String descricao);
	
	@Query("select e from Despesa e where year(e.data) = ?1 and month(e.data) = ?2")
	List<Despesa> findByYearAndMonth(int ano, int mes);
	
	@Query("select SUM(e.valor) from Despesa e where year(e.data) = ?1 and month(e.data) = ?2")
	BigDecimal findByTotalDespesa(int ano, int mes);
	
	@Query("select new br.com.alura.challenge.backend.dto.CategoriaTotalMesDto(c.descricao, SUM(d.valor)) FROM Despesa d RIGHT JOIN d.categoria c ON (year(d.data) = ?1 and month(d.data) = ?2) GROUP BY c.descricao")
	List<CategoriaTotalMesDto> buscarTotalValorCategoria(int ano, int mes);
	
}
