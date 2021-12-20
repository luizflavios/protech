package protech.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import protech.util.HibernateUtil;

@Named
@SuppressWarnings("unchecked")
public class DaoGeneric<E> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private HibernateUtil hibernateUtil;

	public E salvarMerge(E e) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		E retorno = manager.merge(e);
		transaction.commit();
		return retorno;
	}

	public List<E> findAll(E e) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		List<E> lista = manager.createQuery("FROM " + e.getClass().getSimpleName() + " ORDER BY id").setMaxResults(5)
				.getResultList();
		transaction.commit();
		return lista;
	}

	public void delete(E e) {
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		manager.remove(manager.contains(e) ? e : manager.merge(e));
		transaction.commit();

	}
	
	public E consultarEntidade(Class<E> entidade, String pk) {		
		
		EntityTransaction transaction = manager.getTransaction(); 
		transaction.begin();		
		E objeto = (E) manager.find(entidade, Long.parseLong(pk)); 		
		transaction.commit();
		return objeto;
		
	}

	public Double pesquisaSoma() {
		Double saldo;
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();

		saldo = (Double) manager.createQuery(
				"SELECT SUM (s.valor) FROM Servico s JOIN Lancamentos lancamentos3 ON lancamentos3.servico.id = s.id where EXTRACT(MONTH from datacadastro) = Extract(month from Now())")
				.getSingleResult();
		transaction.commit();

		return saldo;
	}

	public Long pesquisaQntd() {
		Long qntd;
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		qntd = (Long) manager.createQuery(
				"SELECT COUNT (1) FROM Lancamentos where EXTRACT(MONTH from datacadastro) = Extract(month from Now())")
				.getSingleResult();
		transaction.commit();
		return qntd;
	}

	public Double pesquisaCusto() {
		Double custo;
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		custo = (Double) manager.createQuery(
				"SELECT SUM(valor) FROM Despesa where EXTRACT(MONTH from datavencimento) = Extract(month from Now())")
				.getSingleResult();
		transaction.commit();

		return custo;
	}

	public Long pesquisaQntdDesp() {
		 
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		Long qntd = (Long) manager.createQuery("SELECT COUNT (1) FROM Despesa where EXTRACT(MONTH from datavencimento) = Extract(month from Now())").getSingleResult() ;
		transaction.commit();
		
		return qntd; 
	}

}
