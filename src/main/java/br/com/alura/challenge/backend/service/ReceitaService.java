package br.com.alura.challenge.backend.service;

import java.util.List;

import br.com.alura.challenge.backend.dto.ReceitaDto;
import br.com.alura.challenge.backend.form.ReceitaForm;
import br.com.alura.challenge.backend.model.Receita;

public interface ReceitaService {
	
	Receita consultarReceita(Long id);
	List<ReceitaDto> buscarReceitasPorMesEAno(int ano, int mes);
	List<ReceitaDto> buscarReceitasPorDescricao(String descricao);
	void removerReceita(Long id);
	ReceitaDto atualizarReceita(Long id, ReceitaForm form);
	ReceitaDto receitaDetalhada(Long id);
	List<ReceitaDto> listarTodasReceitas();
	ReceitaDto cadastrarReceita(ReceitaForm form);
	boolean checarDuplicidade(String descricao, Long id);

}
