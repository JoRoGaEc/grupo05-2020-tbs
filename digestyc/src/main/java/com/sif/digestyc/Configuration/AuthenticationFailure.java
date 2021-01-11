package com.sif.digestyc.Configuration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.sif.digestyc.Controller.Security.LoginController;
import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

public class AuthenticationFailure implements AuthenticationFailureHandler{
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		String username = request.getParameter("username");
		String redireccion = "/login?error=true";
		LOG.info(" username: " +username);
        if(username != null) {
        	Usuario usuario = usuarioService.findByUsername(username);
        	if(usuario != null) {
        		if(usuario.getIntentosRestantes()<0) usuario.setEnabled(false);        		
        		usuario.setIntentosRestantes(usuario.getIntentosRestantes() - 1);
        		try {
            		usuarioService.update(usuario);
            		redireccion = redireccion+"&intento="+ (usuario.getIntentosRestantes()>0?usuario.getIntentosRestantes():0);
        		}catch (Exception e) {
        			LOG.info(e.getMessage());
				}
        	}
        }
        response.sendRedirect(redireccion);
	}
	
	
	
	

}
