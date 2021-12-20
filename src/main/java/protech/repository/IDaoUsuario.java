package protech.repository;

import java.util.List;

import protech.model.Usuario;

public interface IDaoUsuario {
	
	public Usuario consultaExistencia(String usuario, String senha);

	public List<Usuario> carregaComData(String dataInicial, String dataFinal);

	public List<Usuario> carregaComParametroEData(String dataInicial, String dataFinal, String parametro);

	public List<Usuario> carregaComParametro(String parametro);

	

}
