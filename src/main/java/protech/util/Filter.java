package protech.util;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import protech.model.Usuario;


@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
	
	@Inject
	private HibernateUtil hibernateUtil;

   
    public Filter() {
        
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(); 
		
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		String url = req.getServletPath();
		
			if(!url.equalsIgnoreCase("/index.jsf") && usuarioLogado == null ) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsf");
				dispatcher.forward(request, response);
				return;
			} else {		
				//Executa acoes de Request e Response
				chain.doFilter(request, response);
			}
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		hibernateUtil.getEntityManager();
	}

}
