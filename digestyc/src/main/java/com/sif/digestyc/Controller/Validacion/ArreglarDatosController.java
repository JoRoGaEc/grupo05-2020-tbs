package com.sif.digestyc.Controller.Validacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.ValidationModule.ErrorTablaDinamica;
import com.sif.digestyc.Entity.ValidationModule.Notificacion;
import com.sif.digestyc.Entity.ValidationModule.TablasDinamicas;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Validation.NotificacionService;
import com.sif.digestyc.Service.Validation.ValidationImpl.DatoTablaServiceImpl;
import com.sif.digestyc.Service.Validation.ValidationImpl.ErrorTablaDinamicaServiceImpl;

@Controller
@RequestMapping("/reparar")
public class ArreglarDatosController {
	private static final String INDEX = "validacion/ArreglarDatos";
	private static final String DATOSCOMPLETOS = "validacion/DatosCompletos";
	private static final String NOTIFICACIONES = "validacion/notificaciones";
	private static final Logger LOG = LoggerFactory.getLogger(ArreglarDatosController.class);
	
	@Autowired
	TablaServiceImpl tablaService;
	
	@Autowired
	@Qualifier("notificacionServiceImpl")
	private NotificacionService notificacionService;
	
	@Autowired
	DatoTablaServiceImpl datoTabla;
	
	@Autowired
	ErrorTablaDinamicaServiceImpl errorService;
	
	@PreAuthorize("hasUrl('/{id}/tabla/{skip}/{inf}/{pre}')")
	@GetMapping("/{id}/tabla/{skip}/{inf}/{pre}")
	public String getIndex(Model model, @PathVariable("id") Long id, @PathVariable("skip") int skip, @PathVariable("inf") int inf, @PathVariable(name = "pre", required = false) String pre, @RequestParam(name = "notificacion", required=false) String notificacionId) {
		Optional<Tabla> t = tablaService.buscar(id);
		
		if(notificacionId!=null) {
			try {
				Optional<Notificacion> n = notificacionService.buscar(Long.parseLong(notificacionId));
				if(n.isPresent()) {
					Notificacion noti = n.get();
					noti.setEstado(true); //ponemos que ya vio la notificacion
					noti = notificacionService.actualizar(noti);
					model.addAttribute("notificacion", noti);
				}
			}catch (Exception e) {}
		}
		
		if(t.isPresent()) {
			List<ErrorTablaDinamica> datos = errorService.obtenerErroresDeTabla(t.get().getId(), inf, inf+skip);
			if(datos.size()>0) {
				long cantidad  = errorService.contarErrores(t.get().getId())/skip;
				if(!datos.isEmpty()) {
					model.addAttribute("cabeceras", datos.get(0));
					model.addAttribute("errores", datos);
					
					/*
					 * Paginacion a mano por que pueden ser miles de datos, asi que a paginarlo desde el back
					 */
					List<Integer> paginas = new ArrayList<Integer>();
					int desde = 0;
					if(cantidad>3) {
						if(inf>30) {
							desde = (int)inf/10-3;
							cantidad = (cantidad - (int)inf/10)>4?(int)inf/10+4:cantidad;
						}else {
							cantidad = 4;
						}
					}
					
					for(int i = desde; i<=cantidad;i++) {
						paginas.add(i);
					}
					if(skip >=10) {
						model.addAttribute("skip", skip);
					}else {
						model.addAttribute("skip", 10);
					}
					model.addAttribute("paginas", paginas);
					model.addAttribute("current", inf);
					model.addAttribute("last", cantidad);
					model.addAttribute("pre", pre);
				}else {
					model.addAttribute("cabeceras", new ErrorTablaDinamica());
					model.addAttribute("errores", null);
				}
			}else {
				return "redirect:/validacion/start_job?withOutError=true";
			}
			model.addAttribute("table", t.get());
		}

		model.addAttribute("skip", skip);
		model.addAttribute("inf", inf);
		return INDEX;
	}
	

	@PreAuthorize("hasUrl('/{id}/datos_completos/{skip}/{inf}/{tablaCorrelativa}')")
	@GetMapping("/{id}/datos_completos/{skip}/{inf}/{tablaCorrelativa}")
	public String getAllData(Model model, @PathVariable("id") Long id, @PathVariable("skip") int skip, @PathVariable("inf") int inf, @PathVariable("tablaCorrelativa") String tablaCorrelativa, @RequestParam(name = "notificacion", required=false) Long notificacionId) {
		Optional<Tabla> t = tablaService.buscar(id);
		if(t.isPresent() && tablaCorrelativa!=null) {
			Tabla tabla = t.get();
			List<TablasDinamicas> datos = new ArrayList<>();
			if(tablaCorrelativa.equalsIgnoreCase("tabla_correlativa") && tabla.getTablaCorrespondencia()!=null) {
				datos = datoTabla.getDataMapOf(tabla.getTablaCorrespondencia().getNombreTablaCorrespondiente(),tabla.getId());
				model.addAttribute("nombre", tabla.getTablaCorrespondencia().getNombreTablaCorrespondiente());
			}else {
				datos = datoTabla.getDataMapOf(t.get().getNombre(),id, t.get().getArchivo().getEntrega().getRegistro().getId(), t.get().getArchivo().getEntrega().getId());
				model.addAttribute("nombre", tabla.getNombre());
			}
			if(!datos.isEmpty()) {
				model.addAttribute("cabeceras", datos.get(0));
				model.addAttribute("datos", datos);
				model.addAttribute("table", tabla);
			}
		}
		if(notificacionId!=null) {
			Optional<Notificacion> n = notificacionService.buscar(notificacionId);
			if(n.isPresent()) {
				Notificacion noti = n.get();
				noti.setEstado(true); //ponemos que ya vio la notificacion
				noti = notificacionService.actualizar(noti);
				model.addAttribute("notificacion", noti);
			}
		}
		
		if(tablaCorrelativa!=null && !tablaCorrelativa.equalsIgnoreCase("verDatos")) {
			model.addAttribute("url", "/estandarizar/start_job");
		}else {
			model.addAttribute("url", "/validacion/start_job");
		}
		
		return DATOSCOMPLETOS;
	}
	
	
	
	@PreAuthorize("hasUrl('/reparar/guardar')")
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute("id") Long id, @ModelAttribute("skip") int skip, @ModelAttribute("inf") int inf, HttpServletRequest request) {
		String retorno = "redirect:/reparar/"+id+"/tabla/10/0/validar"; //Si le ponemos paginacion, regresamos al incio
		Optional<Tabla> t = tablaService.buscar(id);
		List<ErrorTablaDinamica> errorsIdDelete = new ArrayList<ErrorTablaDinamica>();
		if(t.isPresent()) {
			List<ErrorTablaDinamica> datos = errorService.obtenerErroresDeTabla(t.get().getId(), inf, inf+skip);
			if(!datos.isEmpty()) {
				for(ErrorTablaDinamica error : datos) {
					if(request.getParameterValues("value"+error.getId()) != null) {
						String valor = request.getParameterValues("value"+error.getId())[0];
						if(datoTabla.updateData(t.get().getNombre(), error.getColumna(), "\'"+valor+"\'" , error.getFila())>0) {
							errorsIdDelete.add(error);
						}
					}
				}
			}
		}
		errorService.delete(errorsIdDelete);
		Tabla tabla = t.get();
		long cantidadErrores = errorService.contarErrores(t.get().getId());
		if(cantidadErrores<1) {
			tabla.setEstado(0);
			tablaService.actualizar(tabla);
			retorno = "redirect:/validacion/start_job?ready=true";
		}
		return retorno;
	}
		

	@PreAuthorize("hasUrl('/reparar/notificaciones')")
	@GetMapping("/notificaciones")
	public String getAllNotificaciones(Model model) {
		model.addAttribute("notificaciones", notificacionService.buscarPorEstado(false));
		return NOTIFICACIONES;
	}
	
	
}



