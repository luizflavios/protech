package protech.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import protech.model.Servico;

@FacesConverter(forClass = Servico.class, value = "servicoConverter")
public class ServicoConverter implements Converter, Serializable{

	
	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		EntityManager manager = CDI.current().select(EntityManager.class).get();
		Servico servico = (Servico) manager.find(Servico.class, Long.parseLong(value));
		return servico;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Servico) {
			return ((Servico) value).getId().toString();
		} else {
			return value.toString();
		}
	}
	
}
