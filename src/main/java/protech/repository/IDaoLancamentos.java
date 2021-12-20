package protech.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import protech.model.Lancamentos;

public interface IDaoLancamentos {
	
	List<Lancamentos> consultarTodos();

	List<SelectItem> listaUsuarios();
	
	List<SelectItem> listaEmpresas();
	
	List<SelectItem> listaServicos();

}
