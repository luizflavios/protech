package protech.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import protech.model.Empresa;
import protech.model.Lancamentos;
import protech.model.Servico;
import protech.model.Usuario;
import protech.repository.IDaoLancamentos;

@SuppressWarnings("unchecked")
@Named
public class DaoLancamentos implements IDaoLancamentos, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager; 

	
	@Override
	public List<Lancamentos> consultarTodos() {
		List<Lancamentos> lista = null; 		
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();		
		lista = manager.createQuery("FROM Lancamentos ORDER BY id").getResultList(); 
		transaction.commit();
		return lista;
	}
	
	
	@Override
	public List<SelectItem> listaUsuarios() {
		List<SelectItem> selectItens = new ArrayList<SelectItem>(); 
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();
		
		List<Usuario> retorno = manager.createQuery("FROM Usuario ORDER BY id").getResultList();
		
		for (Usuario pessoa : retorno) {
			selectItens.add(new SelectItem(pessoa, pessoa.getNomeCompleto()));
		}
		
		transaction.commit();
		return selectItens;
		
	}

	@Override
	public List<SelectItem> listaEmpresas() {
		List<SelectItem> selectItens = new ArrayList<SelectItem>(); 
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();
		
		List<Empresa> retorno = manager.createQuery("FROM Empresa ORDER BY id").getResultList();
		
		for (Empresa empresa : retorno) {
			selectItens.add(new SelectItem(empresa, empresa.getNome()));
		}
		
		transaction.commit();
		return selectItens;
	}

	@Override
	public List<SelectItem> listaServicos() {
		List<SelectItem> selectItens = new ArrayList<SelectItem>(); 
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();
		
		List<Servico> retorno = manager.createQuery("FROM Servico ORDER BY id").getResultList();
		
		for (Servico servico : retorno) {
			selectItens.add(new SelectItem(servico, servico.getNome()));
		}
		
		transaction.commit();
		return selectItens;
	}
	
	
	
	

}
