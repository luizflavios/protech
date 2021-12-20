package protech.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;

import protech.dao.DaoGeneric;
import protech.model.Empresa;
import protech.model.Endereco;
import protech.repository.IDaoEmpresa;

@ViewScoped
@Named(value = "beanEmpresa")
public class BeanEmpresa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Empresa empresa = new Empresa();
	private Endereco endereco = new Endereco();
	private List<Empresa> empresas = new ArrayList<Empresa>();
	private String parametro; 

	@Inject
	private DaoGeneric<Empresa> daoGenericEmpresa;
	
	@Inject
	private DaoGeneric<Endereco> daoGenericEndereco;
	
	@Inject
	private IDaoEmpresa daoEmpresa;
	
	
	public String salvar() {
		endereco = daoGenericEndereco.salvarMerge(endereco);
		empresa.setEndereco(endereco);
		empresa = daoGenericEmpresa.salvarMerge(empresa); 
		message("CADASTRO REALIZADO COM SUCESSO"); 
		empresa = new Empresa();
		endereco = new Endereco(); 
		return"";
	}
	
	public void carregarEmpresas() {
		empresas = daoGenericEmpresa.findAll(empresa); 
	}
	
	public void carregaPesquisa() {
		if (parametro.equalsIgnoreCase("Geral")) {
			carregarEmpresas();
		}
	}
	
	public String editar() {
		
		return "cadUsuario.jsf";
	}
	
	public void deletar() {
		empresas.remove(empresa);
		daoGenericEmpresa.delete(empresa);			
	}
	
	
	
	/* Metodos Uteis */
	
	private void message(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MENSAGEM:", msg));
	}
	
	public void pesquisaCep(AjaxBehaviorEvent event) {
		
		try {
			
			URL url = new URL("https://viacep.com.br/ws/" + endereco.getCep().replaceAll("\\p{Punct}", "") + "/json/"); 
			URLConnection urlConnection = url.openConnection(); 
			InputStream input = urlConnection.getInputStream(); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8")); 
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while ((cep = reader.readLine()) != null ) {
				jsonCep.append(cep);
			}
			
			Endereco gson = new Gson().fromJson(jsonCep.toString(), Endereco.class);
			
			endereco.setLogradouro(gson.getLogradouro());
			endereco.setBairro(gson.getBairro());
			endereco.setLocalidade(gson.getLocalidade());
			endereco.setUf(gson.getUf());		
			
			
		} catch (Exception e) {
			e.printStackTrace();
			message("Erro no CEP");
		}
		
	}

	

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public DaoGeneric<Empresa> getDaoGenericEmpresa() {
		return daoGenericEmpresa;
	}

	public void setDaoGenericEmpresa(DaoGeneric<Empresa> daoGenericEmpresa) {
		this.daoGenericEmpresa = daoGenericEmpresa;
	}

	public DaoGeneric<Endereco> getDaoGenericEndereco() {
		return daoGenericEndereco;
	}

	public void setDaoGenericEndereco(DaoGeneric<Endereco> daoGenericEndereco) {
		this.daoGenericEndereco = daoGenericEndereco;
	}

	public IDaoEmpresa getDaoEmpresa() {
		return daoEmpresa;
	}

	public void setDaoEmpresa(IDaoEmpresa daoEmpresa) {
		this.daoEmpresa = daoEmpresa;
	}


	public String getParametro() {
		return parametro;
	}


	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	

}
