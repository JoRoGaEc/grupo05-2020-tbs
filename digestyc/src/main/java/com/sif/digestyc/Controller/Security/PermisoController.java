package com.sif.digestyc.Controller.Security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Constant.ViewConstant;
import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.Security.Permiso;
import com.sif.digestyc.Service.Security.PermisoService;

@Controller
@RequestMapping("/permisos")
public class PermisoController {
	private static final String LISTADO_PERMISOS="/security/listadopermisos";
	
	
	@Autowired
	@Qualifier("permisoServiceImpl")
	private PermisoService permisoService;

	@PreAuthorize("hasUrl('/permisos/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoPermisos(Model model, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success,@RequestParam(name = "unico", required = false) String unico) {
		ModelAndView mav  =  new ModelAndView(LISTADO_PERMISOS);
	    mav.addObject("permisos", permisoService.findAll());
	    mav.addObject("error", error);
	    mav.addObject("success", success);
	    mav.addObject("unico", unico);
	    Permiso permiso = new Permiso();
	    model.addAttribute("permisoModel", permiso);
	    return mav;
	}
	
	
	@PreAuthorize("hasUrl('/permisos/addpermiso')")	
	@PostMapping("/addpermiso")
	public String agregarPermiso(@Valid @ModelAttribute(name="permisoModel") Permiso permiso, BindingResult bindinResult,Model model ) {
		String redireccion = "redirect:/permisos/listar";
		try {
			if (!bindinResult.hasErrors() && permiso != null) {
				permisoService.save(permiso);
				redireccion = redireccion + "?success=insert";
			} else {
				redireccion = redireccion + "?error=insert";
			}
		} catch (Exception e) {
			redireccion = redireccion + "?unico=insert";
		}
		return redireccion;
	}
	
	@PreAuthorize("hasUrl('/permisos/removepermiso')")	
	@PostMapping("/removepermiso")
	public String eliminarPermiso(@ModelAttribute(name="id_eliminar") Long id) {
		String retorno = "redirect:/permisos/listar";
		try {
			permisoService.delete(new Permiso(id));
			retorno = retorno + "?success=delete";
		}catch (Exception e) {
			retorno = retorno + "?error=delete";
		}
		return retorno;
	}
	
	@PreAuthorize("hasUrl('/permisos/editpermiso')")
	@PostMapping("/editpermiso")
	public String editarPermiso(Model model, @ModelAttribute("editar_permiso_id") int id,
								@ModelAttribute("editar_permiso_nombre") String nombre,
								@ModelAttribute("editar_permiso_ubicacion") String ubicacion) {
		String redireccion = "redirect:/permisos/listar";
		try {
			Permiso permiso = permisoService.findById(id);
			System.out.println("ESTE ES EL PERMISO ENCONTRADO" + permiso);
			if (permiso != null && nombre != null && nombre.length() < 128 && ubicacion.length() < 300) {
				permiso.setNombre(nombre);
				permiso.setUbicacion(ubicacion);
				permisoService.update(permiso);
				redireccion = redireccion + "?success=edit";
			} else {
				redireccion = redireccion + "?error=edit";
			}
		} catch (Exception e) {
			redireccion = redireccion + "?unico=edit";
		}
		return redireccion;
	}
	
	@PreAuthorize("hasUrl('/permisos/permisoform')")	
	@GetMapping("/permisoform")
	public void permisoForm(@RequestParam(name="id", required=false) int id,
			Model model) {
		Permiso permiso  = new Permiso();
		
		if(id!=0){
			 permiso = permisoService.findById(id);
		}
		model.addAttribute("permisoModel", permiso);
		
	}
	
	
	
	
	
	
	
}
