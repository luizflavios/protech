package protech.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import protech.dao.DaoGeneric;
import protech.model.Despesa;
import protech.model.Lancamentos;
import protech.model.Servico;

@ViewScoped
@Named
public class BeanHome implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DashboardModel model;
	
	private Double saldo; 
	
	private Long qntd; 
	
	private Long qntdDesp;
	
	private Double custo; 
	
	private Double liquido; 
	
	@Inject
	private DaoGeneric<Servico> daoGeneric; 
	
	@Inject
	private DaoGeneric<Lancamentos> daoGenericLanca;
	
	@Inject
	private DaoGeneric<Despesa> daoGenericDesp;
	
		
		private void carregaHome() {
			saldo = daoGeneric.pesquisaSoma();
			qntd = daoGenericLanca.pesquisaQntd();
			custo = daoGenericDesp.pesquisaCusto();
			qntdDesp = daoGenericDesp.pesquisaQntdDesp(); 
			liquido = saldo - custo;
			
		}
	
	  @PostConstruct
	    public void init() {
	        model = new DefaultDashboardModel();
	        DashboardColumn column1 = new DefaultDashboardColumn();
	        DashboardColumn column2 = new DefaultDashboardColumn();
	        DashboardColumn column3 = new DefaultDashboardColumn();
	        DashboardColumn column4 = new DefaultDashboardColumn();
	        DashboardColumn column5 = new DefaultDashboardColumn();
	        
	        column1.addWidget("qntd");
//	        column1.addWidget("finance");

	        column2.addWidget("qntdDespesas");
	        
	        column3.addWidget("custo");
	        
	        column4.addWidget("saldo");
	        
	        column5.addWidget("liquido");
	    
	        model.addColumn(column1);
	        model.addColumn(column2);
	        model.addColumn(column3);
	        model.addColumn(column4);
	        model.addColumn(column5);
	        
	        carregaHome();
	        
	    }
	  
	  public void handleReorder(DashboardReorderEvent event) {
	        FacesMessage message = new FacesMessage();
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
	        message.setSummary("Reordered: " + event.getWidgetId());
	        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex()
	                + ", Sender index: " + event.getSenderColumnIndex());

	        addMessage(message);
	    }

	    public void handleClose(CloseEvent event) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed",
	                "Closed panel id:'" + event.getComponent().getId() + "'");

	        addMessage(message);
	    }

	    public void handleToggle(ToggleEvent event) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled",
	                "Status:" + event.getVisibility().name());

	        addMessage(message);
	    }

	    private void addMessage(FacesMessage message) {
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }

	public DashboardModel getModel() {
		return model;
	}

	public void setModel(DashboardModel model) {
		this.model = model;
	} 
	
	public Long getQntd() {
		return qntd;
	}
	
	public void setQntd(Long qntd) {
		this.qntd = qntd;
	}
	
	public Double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public Double getLiquido() {
		return liquido;
	}

	public void setLiquido(Double liquido) {
		this.liquido = liquido;
	}

	public Long getQntdDesp() {
		return qntdDesp;
	}

	public void setQntdDesp(Long qntdDesp) {
		this.qntdDesp = qntdDesp;
	}
}
