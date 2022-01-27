package br.com.alura.challenge.backend.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



@Repository
public class ResumoRepository{
	
	
    private EntityManager entityManager;
    private Gson gson;
	
    @Autowired
	ResumoRepository(EntityManager entityManager, Gson gson){
		this.entityManager = entityManager;
		this.gson = gson;
	}
      

    public List<BigDecimal> relatorioSaldoMes(int ano, int mes) {
    	//[0] = receitaTotal, [1] = despesaTotal, [2] = saldoFinal
		Query query = entityManager.createNativeQuery("select ISNULL(ReceitasTotal, 0) as ReceitaTotal,  ISNULL(DespesasTotal, 0) as DespesaTotal, (ISNULL(ReceitasTotal, 0) - ISNULL(DespesasTotal, 0)) as SaldoFinal from (select (select sum(valor) from receita where year(DATA) = :ano and month(DATA) = :mes) as ReceitasTotal, (select sum(valor) from despesa where year(DATA) = :ano and month(DATA) = :mes) as DespesasTotal)")
        .setParameter("ano", ano)
        .setParameter("mes", mes);
			
    	return gson.fromJson(gson.toJson(query.getSingleResult()), new TypeToken<ArrayList<BigDecimal>>(){}.getType());   	
     
    }
    
    public BigDecimal buscarValorTotalCategoria(long id, int ano, int mes) {
    	Query query = entityManager.createNativeQuery("select ISNULL(sum(valor),0) as valor from Despesa where categoria_id = :id and (year(DATA) = :ano and month(DATA) = :mes)")
        .setParameter("id", id)
    	.setParameter("ano", ano)
        .setParameter("mes", mes);
    
    return gson.fromJson(gson.toJson(query.getSingleResult()), new TypeToken<BigDecimal>(){}.getType());
     
    }

}
