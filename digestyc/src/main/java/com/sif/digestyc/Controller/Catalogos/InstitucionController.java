package com.sif.digestyc.Controller.Catalogos;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Constant.ViewConstant;
import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Service.Load.InstitucionService;

@Controller
@RequestMapping("/instituciones")
public class InstitucionController {

	private static final String FORM_LISTAR_INSTITUCIONES = "catalogos/listadoInstituciones";

	@Autowired
	@Qualifier("institucionServiceImpl")
	private InstitucionService institucionService;

	@PreAuthorize("hasUrl('/instituciones/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoInstituciones(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "unico", required = false) String unico) {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_INSTITUCIONES);
		mav.addObject("instituciones", institucionService.listarInstituciones());
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("unico", unico);
		Institucion institucion = new Institucion();
		model.addAttribute("institucionObject", institucion);
		return mav;
	}

	@PreAuthorize("hasUrl('/instituciones/removeinstitucion')")
	@PostMapping("/removeinstitucion")
	public String removeInstitucion(@ModelAttribute(name = "id_eliminar") int id) {
		/*
		 * institucionService.eliminarInstitucion((long) id); return
		 * "redirect:/instituciones/listar";
		 */
		String redireccion = "redirect:/instituciones/listar";
		try {
			Institucion institucion = institucionService.buscarInstitucionPorId((long) id);
			if (institucion != null) {
				institucionService.eliminarInstitucion((long) id);
			}
			redireccion = redireccion + "?success=delete";
		} catch (Exception e) {
			redireccion = redireccion + "?error=delete";
		}

		return redireccion;

	}

	@PreAuthorize("hasUrl('/instituciones/institucionform')")
	@GetMapping("/institucionform")
	public void institucionForm(@RequestParam(name = "id", required = false) Long id, Model model) {
		Institucion institucion = new Institucion();

		if (id != 0) {
			institucion = institucionService.buscarInstitucionPorId(id);
		}
		model.addAttribute("institucionObject", institucion);

	}

	@PreAuthorize("hasUrl('/instituciones/addinstitucion')")
	@PostMapping("/addinstitucion")
	public String agregarInstitucion(@Valid @ModelAttribute(name = "institucionObject") Institucion institucion,
			BindingResult bindinResult, Model model) {
		/*
		 * if(null != institucionService.agregarInstitucion(institucion)) {
		 * model.addAttribute("result",ViewConstant.REGISTRO_GUARDADO); } //Al servicio
		 * le pasamos el contactModel else {
		 * model.addAttribute("result",ViewConstant.REGISTRO_NO_GUARDADO); } return
		 * "redirect:/instituciones/listar?success=insert";
		 */
		String redireccion = "redirect:/instituciones/listar";
		try {
			if (!bindinResult.hasErrors() && institucion != null) {
				institucionService.agregarInstitucion(institucion);
				redireccion = redireccion + "?success=insert";
			} else {
				redireccion = redireccion + "?error=insert";
			}
		} catch (Exception e) {
			redireccion = redireccion + "?unico=insert";
		}
		return redireccion;
	}

	@PreAuthorize("hasUrl('/instituciones/editinstitucion')")
	@PostMapping("/editinstitucion")
	public String editarInstitucion(Model model, @ModelAttribute("id_editar_institucion") int id,
			@ModelAttribute("editar_nombre") String nombre) {
		String redireccion = "redirect:/instituciones/listar";
		try {
			Institucion institucion = institucionService.buscarInstitucionPorId((long) id);
			if (institucion != null && nombre != null && nombre.length() < 128) {
				institucion.setNombre(nombre);
				institucionService.actualizadInstitucion(institucion);
				redireccion = redireccion + "?success=edit";
			} else {
				redireccion = redireccion + "?error=edit";
			}
		} catch (Exception e) {
			redireccion = redireccion + "?unico=edit";
		}
		return redireccion;
	}

}
