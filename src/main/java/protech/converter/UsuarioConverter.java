package protech.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

import protech.model.Usuario;

@FacesConverter(forClass = Usuario.class, value = "usuarioConverter")
public class UsuarioConverter implements Converter, Serializable{

	
	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		EntityManager manager = CDI.current().select(EntityManager.class).get();
		Usuario usuario = (Usuario) manager.find(Usuario.class, Long.parseLong(value));
		return usuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		if (value instanceof Usuario) {
			return ((Usuario) value).getId().toString();
		} else {
			return value.toString();
		}
	}

}
