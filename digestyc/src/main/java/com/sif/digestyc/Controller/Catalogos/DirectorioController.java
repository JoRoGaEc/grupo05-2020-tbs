package com.sif.digestyc.Controller.Catalogos;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Service.Load.CatalogosImpl.DirectorioServiceImpl;

@Controller
@RequestMapping("/directorio")
public class DirectorioController {
	
	private static final String INDEX = "catalogos/directorio";
	
	@Autowired
	@Qualifier("directorioServiceImpl")
	private DirectorioServiceImpl directorioService;

	@PreAuthorize("hasUrl('/directorio/crear')")
	@GetMapping("/crear")
	public ModelAndView crear(@RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		ModelAndView mav = new ModelAndView(INDEX);
	    mav.addObject("error", error);
	    mav.addObject("success", success);
		mav.addObject("directorio", new Directorio());
		mav.addObject("directorios", directorioService.obtenerDirectorios());
		return mav;
	}
	
	@PreAuthorize("hasUrl('/directorio/guardar')")
	@PostMapping("/guardar")
	public ModelAndView crearDirectorio(@Valid @ModelAttribute(name="directorio") Directorio directorio, BindingResult result, @ModelAttribute(name="root") String root) {
		ModelAndView mav = new ModelAndView("redirect:/directorio/crear");
		if(!result.hasErrors()) {
			if(directorioService.actualizar(directorio, !root.isEmpty())!=null) {
				mav.addObject("success", "update");
			}else {
				mav.addObject("error", "update");
			}
		}else {
			mav.addObject("error", "insert");
		}
		return mav;
	}
	
	
	
}
