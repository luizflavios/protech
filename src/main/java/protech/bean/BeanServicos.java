package protech.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import protech.dao.DaoGeneric;
import protech.model.Servico;
import protech.repository.IDaoServico;

@ViewScoped
@Named(value = "beanServicos")
public class BeanServicos implements Serializable{

	
	private static final long serialVersionUID = 1L;	
	
	private Servico servico = new Servico(); 
	private String parametro; 
	private List<Servico> lista = new ArrayList<Servico>(); 
	
	@Inject
	private DaoGeneric<Servico> daoGenericServico;
	
	@Inject
	private IDaoServico daoServico;
	
	
	public String salvar() {
		servico = daoGenericServico.salvarMerge(servico);
		message("Serviço salvo com sucesso");
		servico = new Servico(); 
		return ""; 
	}
	
	public void carregarServicos() {
		lista = daoGenericServico.findAll(servico); 
	}
	
	public void carregaPesquisa() {
		if (parametro.equalsIgnoreCase("Geral")) {
			carregarServicos();
		}
	}
	
	public String editar() {
		
		return "cadUsuario.jsf";
	}
	
	public void deletar() {

		lista.remove(servico);
		daoGenericServico.delete(servico);
		
	}
		
	
	
	/* Métodos Uteis */
	
	private void message(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MENSAGEM:", msg));
	}
	
	
	
	
	
	

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public DaoGeneric<Servico> getDaoGenericServico() {
		return daoGenericServico;
	}

	public void setDaoGenericServico(DaoGeneric<Servico> daoGenericServico) {
		this.daoGenericServico = daoGenericServico;
	}

	public IDaoServico getDaoServico() {
		return daoServico;
	}

	public void setDaoServico(IDaoServico daoServico) {
		this.daoServico = daoServico;
	}
	
	public List<Servico> getLista() {
		return lista;
	}
	
	public void setLista(List<Servico> lista) {
		this.lista = lista;
	}

}
