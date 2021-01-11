package com.sif.digestyc.Controller.Security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.sif.digestyc.Entity.Security.Permiso;
import com.sif.digestyc.Entity.Security.Role;
import com.sif.digestyc.Service.Security.PermisoService;
import com.sif.digestyc.Service.Security.RolService;

@Controller
@RequestMapping("/roles")
public class RolController {

	public static final String LISTADO_ROLES = "/security/listadoroles";
	public static final String ROL_PERMISO = "/security/agregarpermiso";
	public static final int TODO = 100;

	@Autowired
	@Qualifier("rolServiceImpl")
	private RolService rolService;

	@Autowired
	@Qualifier("permisoServiceImpl")
	private PermisoService permisoService;

	@PreAuthorize("hasUrl('/roles/listar')")
	@RequestMapping("/listar")
	public ModelAndView listarRoles(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "unico", required = false) String unico) {
		ModelAndView mav = new ModelAndView(LISTADO_ROLES);
		mav.addObject("roles", rolService.findAll());
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("unico", unico);
		Role rol = new Role();
		model.addAttribute("rolModel", rol);
		return mav;
	}

	@PreAuthorize("hasUrl('/roles/addrol')")
	@PostMapping("/addrol")
	public String agregarRol(@Valid @ModelAttribute(name = "rolModel") Role rol, BindingResult bindinResult,
			Model model) {
		String redireccion = "redirect:/roles/listar";
		try {
			if (!bindinResult.hasErrors() && rol != null) {
				rolService.save(rol);
				redireccion = redireccion + "?success=insert";
			} else {
				redireccion = redireccion + "?error=insert";
			}
		} catch (Exception e) {
			redireccion = redireccion + "?unico=insert";
		}
		return redireccion;
	}

	@PreAuthorize("hasUrl('/roles/removerol')")
	@RequestMapping("/removerol")
	public String eliminarRol(@ModelAttribute(name = "id_eliminar") int id) {
		String redireccion = "redirect:/roles/listar";
		try {
			Role rolDb = rolService.findById(id);
			if (rolDb != null) {
				rolService.delete(rolDb);
			}
			redireccion = redireccion + "?success=delete";
		} catch (Exception e) {
			redireccion = redireccion + "?error=delete";
		}

		return redireccion;
	}

	@PreAuthorize("hasUrl('/roles/editarrol')")
	@PostMapping("/editrol")
	public String editarRol(Model model, @ModelAttribute(name = "editar_rol_id") int id,
			@ModelAttribute(name = "editar_rol_nombre") String nombre,
			@ModelAttribute(name = "editar_rol_descripcion") String descripcion,
			@ModelAttribute(name = "editar_rol_codigo") String codigo) {
		String redireccion = "redirect:/roles/listar";

		try {
			Role rolDb = rolService.findById(id);
			if (rolDb != null && nombre.length() < 50 && codigo.length() < 20 && descripcion.length() < 1024) {
				rolDb.setNombre(nombre);
				rolDb.setDescripcion(descripcion);
				rolDb.setCodigo(codigo);
				rolService.update(rolDb);
				redireccion = redireccion + "?success=edit";
			} else {
				redireccion = redireccion + "?error=edit";
			}
		} catch (Exception e) {
			redireccion = redireccion + "?unico=edit";
		}
		return redireccion;

	}

	@PreAuthorize("hasUrl('/roles/rolform')")
	@GetMapping("/rolform")
	public void permisoForm(@RequestParam(name = "id", required = false) int id, Model model) {
		Role rol = new Role();

		if (id != 0) {
			rol = rolService.findById(id);
		}
		model.addAttribute("rolModel", rol);

	}

	@PreAuthorize("hasUrl('/roles/permisos/agregarpermiso/')")
	@GetMapping("/agregarpermiso/{id}")
	public ModelAndView AgregarPermiso(@PathVariable("id") int id,
			@RequestParam(name = "error", required = false) String error) {
		ModelAndView mav = new ModelAndView(ROL_PERMISO);
		Role rol = rolService.findById(id);
		List<Permiso> permisos = permisoService.findAllLessRole(id);
		mav.addObject("rol", rol);
		mav.addObject("permisos", permisos);
		mav.addObject("error", error);
		return mav;
	}

	@PreAuthorize("hasUrl('/roles/eliminarpermiso')")
	@PostMapping("/eliminarpermiso")
	public String eliminarPermisos(Model model, @ModelAttribute(name = "eliminar_rol_permiso_id") int id,
			@ModelAttribute(name = "eliminar_todo") int elimnarTodo, HttpServletRequest request) {
		Role rol = rolService.findById(id);
		List<Permiso> permisos = rol.getPermisos();
		if (elimnarTodo == TODO) {
			permisos.clear();
		} else {
			if (elimnarTodo == 0 && request.getParameterValues("quitarPermiso") != null) {
				for (String permiso_id : request.getParameterValues("quitarPermiso")) {
					int permiso = Integer.parseInt(permiso_id);
					permisos.remove(permisoService.findById(permiso));
				}
			}
		}
		rolService.update(rol);
		return "redirect:/roles/agregarpermiso/" + id + "/";
	}

	@PreAuthorize("hasUrl('/roles/guardarpermiso')")
	@PostMapping("/guardarpermiso")
	public String guardarPermisos(Model model, @ModelAttribute(name = "agregar_rol_permiso_id") int id,
			@ModelAttribute(name = "agregarTodoPermiso") int agregarTodoPermiso, HttpServletRequest request) {
		Role rol = rolService.findById(id);
		if (agregarTodoPermiso == TODO) {
			rol.setPermisos(permisoService.findAll());
		} else {
			if (agregarTodoPermiso == 0 && request.getParameterValues("guardarPermiso") != null) {
				for (String permiso_id : request.getParameterValues("guardarPermiso")) {
					int permiso = Integer.parseInt(permiso_id);
					rol.setPermiso(permisoService.findById(permiso));
				}
			}
		}
		rolService.update(rol);
		return "redirect:/roles/agregarpermiso/" + id + "/";
	}

}
