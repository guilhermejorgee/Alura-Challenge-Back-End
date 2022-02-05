package br.com.alura.challenge.backend.service;

import java.util.List;

import br.com.alura.challenge.backend.dto.DespesaDto;
import br.com.alura.challenge.backend.form.AtualizacaoDespesaForm;
import br.com.alura.challenge.backend.form.DespesaForm;
import br.com.alura.challenge.backend.model.Categoria;
import br.com.alura.challenge.backend.model.Despesa;

public interface DespesaService {
	
	Despesa consultarDespesa(Long id);
	List<DespesaDto> buscarDespesasPorMesEAno(int ano, int mes);
	List<DespesaDto> buscarDespesasPorDescricao(String descricao);
	void removerDespesa(Long id);
	DespesaDto atualizarDespesa(Long id, AtualizacaoDespesaForm form);
	DespesaDto despesaDetalhada(Long id);
	List<DespesaDto> listarTodasDespesas();
	DespesaDto cadastrarDespesa(DespesaForm form);
	boolean checarDuplicidade(String descricao, Long id);
	Categoria checarCategoria(String nomeCategoria);


}
