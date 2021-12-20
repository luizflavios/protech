package protech.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import protech.dao.DaoGeneric;
import protech.model.Lancamentos;
import protech.repository.IDaoLancamentos;

@ViewScoped
@Named(value = "beanLancamentos")
public class BeanLancamentos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Lancamentos lancamento = new Lancamentos(); 
	private List<Lancamentos> lista = new ArrayList<Lancamentos>();
	private List<SelectItem> usuarios; 
	private List<SelectItem> empresas; 
	private List<SelectItem> servicos;
	private String parametro; 
	
	@Inject
	private DaoGeneric<Lancamentos> daoGeneric;
	
	@Inject
	private IDaoLancamentos daoLancamentos; 
	
	public String salvar() {				  
		 lancamento = daoGeneric.salvarMerge(lancamento);
		 lancamento = new Lancamentos();
		 message("Lancamento cadastrado com sucesso!");
		 return "";
	}
	
	public void carregaPesquisa() {
		if (parametro.equalsIgnoreCase("Geral")) {
			carregarLancamentos();
		}
	}
		
	public void carregarLancamentos() {
		lista = daoGeneric.findAll(lancamento); 
		
	}

	private void message(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MENSAGEM:", msg));
	}
	
	public Lancamentos getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamentos lancamento) {
		this.lancamento = lancamento;
	}
	public List<Lancamentos> getLista() {
		return lista;
	}
	public void setLista(List<Lancamentos> lista) {
		this.lista = lista;
	}
	public List<SelectItem> getUsuarios() {
		usuarios = daoLancamentos.listaUsuarios();
		return usuarios;
	}
	public void setUsuarios(List<SelectItem> usuarios) {
		this.usuarios = usuarios;
	}
	public List<SelectItem> getEmpresas() {
		empresas = daoLancamentos.listaEmpresas();
		return empresas;
	}
	public void setEmpresas(List<SelectItem> empresas) {
		this.empresas = empresas;
	}
	public List<SelectItem> getServicos() {
		servicos = daoLancamentos.listaServicos();
		return servicos;
	}
	public void setServicos(List<SelectItem> servicos) {
		this.servicos = servicos;
	}



	public String getParametro() {
		return parametro;
	}



	public void setParametro(String parametro) {
		this.parametro = parametro;
	} 
	
	
	

	
	
	
}
