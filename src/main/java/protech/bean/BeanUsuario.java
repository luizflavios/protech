package protech.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import protech.dao.DaoGeneric;
import protech.model.Endereco;
import protech.model.Usuario;
import protech.repository.IDaoUsuario;
import protech.util.HibernateUtil;

@ViewScoped
@Named(value = "beanUsuario")
public class BeanUsuario implements Serializable{

	
	private static final long serialVersionUID = 1L;	
	
	private Usuario usuario = new Usuario();
	private Endereco endereco = new Endereco(); 
	private List<Usuario> lista = new ArrayList<Usuario>();
	private String parametro;
	
	
	private Date dataInicial;
	
	
	private Date dataFinal;

	@Inject
	private DaoGeneric<Usuario> daoGenericUsuario;
	
	@Inject
	private DaoGeneric<Endereco> daoGenericEndereco;
	
	@Inject
	private IDaoUsuario daoUsuario;
	
	@Inject
	private HibernateUtil hibernateUtil; 
	
	/* Métodos de Validacao */
	
	public String logar() {
		
		Usuario user = daoUsuario.consultaExistencia(usuario.getUsuario(), usuario.getSenha());
		
		if(user != null) {
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext(); 
			HttpServletRequest request = (HttpServletRequest) externalContext.getRequest(); 
			HttpSession session = request.getSession(); 
			
			session.setAttribute("usuarioLogado", usuario);
			
			return "pages/home.jsf";
			
		}
		
		return "index.jsf"; 
	}
	
	public String deslogar() {		
		
		 FacesContext context = FacesContext.getCurrentInstance();
		 ExternalContext external = context.getExternalContext();
		 
		 HttpServletRequest req = (HttpServletRequest) external.getRequest();
		 HttpSession session = req.getSession();
		 
		 session.removeAttribute("usuarioLogado");
		 session.invalidate();
		 
		 return "/index.jsf";
		
	}
	
	public boolean permiteAcesso(String acesso) {
		
		 FacesContext context = FacesContext.getCurrentInstance();
		 ExternalContext external = context.getExternalContext();
		 
		 HttpServletRequest req = (HttpServletRequest) external.getRequest();
		 HttpSession session = req.getSession();
		 
		 Usuario user = (Usuario) session.getAttribute("usuarioLogado");
		 
		 return user.getPerfil().equals(acesso);
		
	}

	/* Métodos Action do Bean */
	
	public String salvar() {
		endereco = daoGenericEndereco.salvarMerge(endereco); 
		usuario.setEndereco(endereco);		
		usuario = daoGenericUsuario.salvarMerge(usuario);
		message("USUARIO CADASTRADO COM SUCESSO");
		usuario = new Usuario();
		endereco = new Endereco(); 
		return ""; 
	}
	
	
	public void carregarUsuarios() {
		lista = daoGenericUsuario.findAll(usuario); 
	}
	
	public void carregaPesquisa() {
		if (parametro.equalsIgnoreCase("Geral")) {
			carregarUsuarios();
		}
	}
	
	public void editar() {
		Object pk = hibernateUtil.getPrimaryKey(usuario);
		
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "cadUsuario.xhtml");
		
		usuario = daoGenericUsuario.consultarEntidade(Usuario.class, pk.toString()); 
		
	}
	
	public void deletar() {

		lista.remove(usuario);
		daoGenericUsuario.delete(usuario);
		
		
	}
	
	public void carregaComData() {
		
		if (dataInicial != null && dataFinal != null) {
		
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dataIn = sdf.format(dataInicial); 
			String dataFn = sdf.format(dataFinal); 		
			lista = daoUsuario.carregaComData(dataIn, dataFn);
		
		} else {
			message("Alguma data encontra-se vazia");
		}
		
	}
	
	public void carregaComParametro(){
		lista = daoUsuario.carregaComParametro(parametro); 
	}
	
	public void carregaComParametroEDAta() {
		
		if (dataInicial != null && dataFinal != null) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dataIn = sdf.format(dataInicial); 
			String dataFn = sdf.format(dataFinal); 
			lista = daoUsuario.carregaComParametroEData(dataIn, dataFn, parametro);
		
		} else {
			message("Alguma data encontra-se vazia");
		}
		

	}
	
	/* Métodos uteis */
	
	private void message(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("MENSAGEM:", msg));
	}
	
	public void pesquisaParametrizada() {
		
		if ((!parametro.isEmpty() || parametro != null) && (dataInicial == null )) {
			carregaComParametro();
		} else if ((parametro.isEmpty() || parametro == null) && (dataInicial != null)) {
			carregaComData();
		} else if ((!parametro.isEmpty() || parametro != null) && (dataInicial != null )) {
			carregaComParametroEDAta();
		}
		
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Usuario> getLista() {
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	} 
	
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	
	public Date getDataInicial() {
		return dataInicial;
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

}
