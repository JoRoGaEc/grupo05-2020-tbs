package com.sif.digestyc.Controller.Security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.servlet.view.RedirectView;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.Security.Role;
import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Service.Load.CatalogosImpl.InstitucionServiceImpl;
import com.sif.digestyc.Service.Security.RolService;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	private static final String REGISTER = "security/register";
	private static final String UPDATE = "security/actualizarusuario";
	private static final String INDEX = "security/listadousuarios";
	public static final String USUARIO_ROL = "security/agregarrol";
	public static final int TODO = 100;

	@Autowired
	@Qualifier("rolServiceImpl")
	private RolService rolService;

	@Autowired
	UsuarioServiceImpl usuarioService;

	@Autowired
	InstitucionServiceImpl institucionService;

	@PreAuthorize("hasUrl('/usuarios/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoUsuarios(@RequestParam(name = "success", required = false) String success) {
		ModelAndView mav = new ModelAndView(INDEX);
		mav.addObject("success", success);
		mav.addObject("usuarios", usuarioService.findAll(true));
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/desactivados')")
	@GetMapping("/desactivados")
	public ModelAndView usuariosDesactivados() {
		ModelAndView mav = new ModelAndView(INDEX);
		mav.addObject("usuarios", usuarioService.findAll(false));
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/listaCompleta')")
	@GetMapping("/listaCompleta")
	public ModelAndView todosUsuarios() {
		ModelAndView mav = new ModelAndView(INDEX);
		mav.addObject("usuarios", usuarioService.findAll());
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/agregarusuario')")
	@GetMapping("/agregarusuario")
	public ModelAndView agregarUsuario(@RequestParam(name = "error", required = false) String error) {
		ModelAndView mav = new ModelAndView(REGISTER);
		mav.addObject("error", error);
		mav.addObject("texto", "texto");
		mav.addObject("usuario", new Usuario());
		mav.addObject("instituciones", institucionService.listarInstituciones());
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/guardarusuario')")
	@PostMapping("/guardarUsuario")
	public ModelAndView saveUser(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result,
			@ModelAttribute("inst_id") String id) {
		ModelAndView mav = new ModelAndView();
		String password = usuario.getPassword();
		if (result.hasErrors()) {
			mav.setViewName(REGISTER);
			mav.addObject("instituciones", institucionService.listarInstituciones());
			return mav;
		}
		try {
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			if (id != null && id.length() > 0) {
				Institucion institucion = institucionService.buscarInstitucionPorId(Long.parseLong(id));
				usuario.setInstitucion(institucion);
			}
			usuario.setPassword(pass.encode(usuario.getPassword()));
			usuarioService.update(usuario);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			usuario.setPassword(password);
			mav.addObject("unicos", "Ya existe un usuario con ese username o correo");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.setViewName(REGISTER);
			return mav;
		}
		mav.addObject("success", true);
		mav.setViewName("redirect:/usuarios/listar");
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/editarusuario')")
	@GetMapping("/editarusuario/{id}/")
	public ModelAndView editarUsuario(@PathVariable("id") int id,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "newPassword", required = false) String newPassword) {
		ModelAndView mav = new ModelAndView(UPDATE);
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("password", password);
		mav.addObject("newPassword", newPassword);
		mav.addObject("accion", "/usuarios/actualizarUsuario");
		mav.addObject("accionPassword", "/usuarios/cambiarPasswordUsuario");
		mav.addObject("cancelar", "/usuarios/listar");
		mav.addObject("tipo", "CONTRASEÑA DEL ADMINISTRADOR *");
		Usuario usuario = usuarioService.findById(id);
		mav.addObject("usuario", usuario);
		mav.addObject("instituciones", institucionService.listarInstituciones());
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/miPerfil')")
	@GetMapping("/miPerfil/")
	public ModelAndView miPerfil(@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "password", required = false) String password,
			@RequestParam(name = "newPassword", required = false) String newPassword) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", error);
		String vista = "redirect:/";
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("password", password);
		mav.addObject("newPassword", newPassword);
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
							.getPrincipal();
					Usuario usuario = usuarioService.findByUsername(user.getUsername());
					mav.addObject("usuario", usuario);
					mav.addObject("accion", "/usuarios/actualizarPerfil");
					mav.addObject("accionPassword", "/usuarios/cambiarPasswordPerfil");
					mav.addObject("cancelar", "/");
					mav.addObject("tipo", "CONTRASEÑA ACTUAL *");
					vista = UPDATE;
				} catch (Exception e) {
					mav.setViewName(vista);
					mav.addObject("instituciones", institucionService.listarInstituciones());
					return mav;
				}
			}
		}
		mav.setViewName(vista);
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/actualizarUsuario')")
	@PostMapping("/actualizarUsuario")
	public ModelAndView actualizarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result,
			@ModelAttribute("inst_id") String id) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.setViewName(UPDATE);
			mav.addObject("error", "Datos invalidos");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			return mav;
		}
		try {
			Usuario usuarioActualizado = usuarioService.findById(usuario.getId().intValue());
			if (usuarioActualizado != null) {
				usuarioActualizado.setApellido(usuario.getApellido());
				usuarioActualizado.setNombre(usuario.getNombre());
				usuarioActualizado.setEmail(usuario.getEmail());
				if (id != null && id.length() > 0) {
					Institucion institucion = institucionService.buscarInstitucionPorId(Long.parseLong(id));
					usuarioActualizado.setInstitucion(institucion);
				}
				usuarioService.update(usuarioActualizado);
			}
			mav.addObject("success", true);
			mav.setViewName("redirect:/usuarios/editarusuario/" + usuarioActualizado.getId() + "/");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mav.addObject("emailExiste", "Ya existe un usuario con ese correo");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.setViewName(UPDATE);
			return mav;
		}
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/actualizarPerfil')")
	@PostMapping("/actualizarPerfil")
	public ModelAndView actualizarPerfil(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.setViewName(UPDATE);
			mav.addObject("error", "Datos invalidos");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			return mav;
		}
		try {
			Usuario usuarioActualizado = usuarioService.findById(usuario.getId().intValue());
			if (usuarioActualizado != null) {
				usuarioActualizado.setApellido(usuario.getApellido());
				usuarioActualizado.setNombre(usuario.getNombre());
				usuarioActualizado.setEmail(usuario.getEmail());
				usuarioService.update(usuarioActualizado);
			}
			mav.addObject("success", true);
			mav.setViewName("redirect:/usuarios/miPerfil/");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mav.addObject("emailExiste", "Ya existe un usuario con ese correo");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.setViewName(UPDATE);
			return mav;
		}
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/cambiarPasswordPerfil')")
	@PostMapping("/cambiarPasswordPerfil")
	public ModelAndView cambiarPasswordPerfil(@ModelAttribute("currentPassword") String currentPassword,
			@ModelAttribute("password") String password, @ModelAttribute("confirmPassword") String confirmPassword) {
		ModelAndView mav = new ModelAndView();
		Usuario usuarioRegistrado = null;
		SecurityContext context = SecurityContextHolder.getContext();
		String redireccion = "redirect:/usuarios/listar";
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
							.getPrincipal();
					usuarioRegistrado = usuarioService.findByUsername(user.getUsername());
				} catch (Exception e) {
					redireccion = "redirect:/";
				}
			}
		}

		if (usuarioRegistrado != null) {
			redireccion = "redirect:/usuarios/miPerfil/";
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			if (pass.matches(currentPassword, usuarioRegistrado.getPassword())) {
				if (password.length() > 7 && password.equals(confirmPassword)
						&& password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
					usuarioRegistrado.setPassword(pass.encode(password));
					usuarioService.update(usuarioRegistrado);
					mav.addObject("success", true);
				} else {
					mav.addObject("newPassword", true);
				}
			} else {
				mav.addObject("password", true);
			}
		}
		mav.setViewName(redireccion);
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/cambiarPasswordUsuario')")
	@PostMapping("/cambiarPasswordUsuario")
	public ModelAndView cambiarPassword(@ModelAttribute("currentPassword") String currentPassword,
			@ModelAttribute("password") String password, @ModelAttribute("confirmPassword") String confirmPassword,
			@ModelAttribute("username") String username) {
		ModelAndView mav = new ModelAndView();
		Usuario usuarioActualizado = usuarioService.findByUsername(username);
		Usuario usuarioRegistrado = null;
		SecurityContext context = SecurityContextHolder.getContext();
		String redireccion = "redirect:/usuarios/listar";
		if (context != null) {
			Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				try {
					org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication
							.getPrincipal();
					usuarioRegistrado = usuarioService.findByUsername(user.getUsername());
				} catch (Exception e) {
					redireccion = "redirect:/";
				}
			}
		}

		if (usuarioActualizado != null && usuarioRegistrado != null) {
			redireccion = "redirect:/usuarios/editarusuario/" + usuarioActualizado.getId() + "/";
			BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
			if (pass.matches(currentPassword, usuarioRegistrado.getPassword())) {
				if (password.length() > 7 && password.equals(confirmPassword)
						&& password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$")) {
					usuarioActualizado.setPassword(pass.encode(password));
					usuarioActualizado.setPrimeraSesion(true);
					usuarioService.update(usuarioActualizado);
					mav.addObject("success", true);
				} else {
					mav.addObject("newPassword", true);
				}
			} else {
				mav.addObject("password", true);
			}
		}
		mav.setViewName(redireccion);
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/removerusuario')")
	@PostMapping("/removerusuario")
	public String removerUsuario(@ModelAttribute("usuario_id") int id) {
		Usuario usuario = usuarioService.findById(id);
		usuario.setEnabled(!usuario.getEnabled());
		usuarioService.update(usuario);
		return "redirect:/usuarios/listar";
	}

	@PreAuthorize("hasUrl('/usuarios/agregarrol')")
	@GetMapping("/agregarrol/{id}/")
	public ModelAndView AgregarPermiso(@PathVariable("id") int id) {
		ModelAndView mav = new ModelAndView(USUARIO_ROL);
		Usuario usuario = usuarioService.findById(id);
		List<Role> roles = rolService.findAllLessUser(id);
		mav.addObject("roles", roles);
		mav.addObject("usuario", usuario);
		return mav;
	}

	@PreAuthorize("hasUrl('/usuarios/eliminarrol')")
	@PostMapping("/eliminarrol")
	public String eliminarPermisos(Model model, @ModelAttribute(name = "usuario_id") int id,
			@ModelAttribute(name = "eliminar_todo") int eliminarTodo, HttpServletRequest request) {
		Usuario usuario = usuarioService.findById(id);
		List<Role> roles = usuario.getRoles();
		if (eliminarTodo == TODO) {
			roles.clear();
		} else {
			if (eliminarTodo == 0 && request.getParameterValues("quitarRol") != null) {
				for (String permiso_id : request.getParameterValues("quitarRol")) {
					int permiso = Integer.parseInt(permiso_id);
					roles.remove(rolService.findById(permiso));
				}
			}
		}
		usuarioService.update(usuario);
		return "redirect:/usuarios/agregarrol/" + id + "/";
	}

	@PreAuthorize("hasUrl('/usuarios/guardarrol')")
	@PostMapping("/guardarrol")
	public RedirectView guardarPermisos(Model model, @ModelAttribute(name = "usuario_id") int id,
			@ModelAttribute(name = "agregarTodoRol") int agregarTodoRol, HttpServletRequest request) {
		Usuario usuario = usuarioService.findById(id);
		if (agregarTodoRol == TODO) {
			usuario.setRoles(rolService.findAll());
		} else {
			if (agregarTodoRol == 0 && request.getParameterValues("guardarRol") != null) {
				for (String rol_id : request.getParameterValues("guardarRol")) {
					int rol = Integer.parseInt(rol_id);
					usuario.setRol(rolService.findById(rol));
				}
			}
		}
		usuarioService.update(usuario);
		RedirectView rv = new RedirectView("/usuarios/agregarrol/" + id + "/");
		return rv;
	}

}
