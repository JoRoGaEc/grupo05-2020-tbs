package com.sif.digestyc.Controller.Security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

@Controller
public class LoginController {
	private static final String LOGIN = "security/login";
	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	private static final String UPDATE = "security/actualizarusuario";
	
	@Autowired
	UsuarioServiceImpl usuarioService;

	@GetMapping("/login")
	public String showLoginForm(Model model, @RequestParam(name = "error", required = false) String error, 
			@RequestParam(name = "intento", required = false) String intento,
			@RequestParam(name = "logout", required = false) String logout) {
		LOG.info("METHOD: " + "showLoginForm() -- PARAMS: error=" + error + ", Logout =  " + logout);
		model.addAttribute("error", error);
		model.addAttribute("logout", logout); // Estos son para que se puedan rellenar en la vista
		model.addAttribute("intento", intento); 
		LOG.info("Returning to login view ");
		return LOGIN; // Usamos la clase de Constantes
	}

	@GetMapping({ "/loginsuccess" })
	public String loginSuccess() {
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Usuario usuario = usuarioService.findByUsername(user.getUsername());
					usuario.setIntentosRestantes(3);
					usuarioService.update(usuario);
					if(usuario.getPrimeraSesion()) {
						return "redirect:/primerInicioSesion";
					}
				} catch (Exception e) {
					LOG.error(e.getMessage());
				}
			}
		}
		
		return "redirect:/primerInicioSesion";
	}

	
	@GetMapping("/primerInicioSesion")
	public ModelAndView primerInicioSesion(@RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success, @RequestParam(name="password", required=false) String password, @RequestParam(name="newPassword", required=false) String newPassword) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", error);
		String vista = "redirect:/login";
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("newPassword",newPassword);
		SecurityContext context = SecurityContextHolder.getContext(); 
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					Usuario usuario = usuarioService.findByUsername(user.getUsername());
					usuario.setIntentosRestantes(3);
					usuarioService.update(usuario);
					
					if(usuario.getPrimeraSesion()) {
						mav.addObject("usuario", usuario);
						mav.addObject("accion","/usuarios/actualizarPerfil");
						mav.addObject("accionPassword","/cambiarPassword");
						mav.addObject("cancelar","/");
						mav.addObject("password", "inicio");
						mav.addObject("tipo","CONTRASEÑA ACTUAL");
						vista = UPDATE;
					}else {
						vista = "redirect:/index";
					}
				} catch (Exception e) {
					mav.setViewName(vista);
					return mav;
				}
			}
		}
		
		mav.setViewName(vista);
		return mav;
	}
	
	@PostMapping("/cambiarPassword")
	public ModelAndView cambiarPassword(HttpServletRequest request, @ModelAttribute("currentPassword") String currentPassword, @ModelAttribute("password") String password, @ModelAttribute("confirmPassword") String confirmPassword){
		ModelAndView mav = new ModelAndView();
		Usuario usuarioRegistrado = null;
		SecurityContext context = SecurityContextHolder.getContext(); 
		String redireccion = "redirect:/primerInicioSesion";
		if (context != null) {
			Authentication authentication = context.getAuthentication(); 
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
					usuarioRegistrado = usuarioService.findByUsername(user.getUsername());
				} catch (Exception e) {
					redireccion = "redirect:/index";
				}
			}
		}

		if(usuarioRegistrado != null) {
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			if(pass.matches(currentPassword, usuarioRegistrado.getPassword())) {
				if(password.length()>7 && password.equals(confirmPassword)) {
					usuarioRegistrado.setPassword(pass.encode(password));
					usuarioRegistrado.setPrimeraSesion(false);
					usuarioService.update(usuarioRegistrado);
					mav.addObject("success",true);
					redireccion = "redirect:/login?logout";
					SecurityContextHolder.clearContext();
					HttpSession session = request.getSession(false);
			        if (session != null) {
			            session.invalidate();
			        }
			        try {
						request.logout();
					} catch (ServletException e) {
						System.out.println(e.getMessage());
					}
				}else {
					mav.addObject("newPassword",true);
				}
			}
			else {
				mav.addObject("password",true);
			}
		}		
		mav.setViewName(redireccion);
		return mav;
	}	
	

}
