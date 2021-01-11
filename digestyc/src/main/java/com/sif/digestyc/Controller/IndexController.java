package com.sif.digestyc.Controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Controller.Security.LoginController;
import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Repository.Validacion.DatoTablaRepository;
import com.sif.digestyc.Service.Load.CargaImpl.CargarArchivoServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

@Controller
public class IndexController {
	private static final String INDEX = "index";
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	UsuarioServiceImpl usuarioService;
	
	@Autowired
	TablaServiceImpl tablaService;

	@Autowired
	CargarArchivoServiceImpl cargaService;

	
	//@PreAuthorize("hasUrl('/index')")
	@GetMapping("/index") //aqui no se como poner el index en el PreAuthorize
	public ModelAndView getIndex() {
		ModelAndView mav = new ModelAndView(INDEX);
		mav.addObject("atributo", "atributo");
		return mav;
	}
	
	
	@GetMapping("") 
	public String getIndexDefault() {
		return "redirect:/primerInicioSesion";
	}

}
