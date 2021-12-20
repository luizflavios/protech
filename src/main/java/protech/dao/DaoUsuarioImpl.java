package protech.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import protech.model.Usuario;
import protech.repository.IDaoUsuario;

@Named
public class DaoUsuarioImpl implements IDaoUsuario, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Override
	public Usuario consultaExistencia(String usuario, String senha) {

		Usuario retorno = null;
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		retorno = (Usuario) manager
				.createQuery("SELECT p FROM Usuario p WHERE p.usuario= '" + usuario + "' AND p.senha= '" + senha + "'")
				.getSingleResult();

		transaction.commit();
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> carregaComData(String dataInicial, String dataFinal) {

		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		List<Usuario> retorno = manager.createQuery(
				"SELECT p FROM Usuario p WHERE p.dtaCadastro BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'")
				.getResultList();

		transaction.commit();
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> carregaComParametro(String parametro) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		List<Usuario> retorno = manager
				.createQuery("SELECT p FROM Usuario p WHERE UPPER (p.nomeCompleto) LIKE UPPER ('%" + parametro + "%')")
				.getResultList();

		transaction.commit();
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> carregaComParametroEData(String dataInicial, String dataFinal, String parametro) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		List<Usuario> retorno = manager
				.createQuery("SELECT p FROM Usuario p WHERE UPPER (p.nomeCompleto) LIKE UPPER ('%" + parametro
						+ "%') AND p.dtaCadastro BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'")
				.getResultList();

		transaction.commit();
		return retorno;
	}

}
