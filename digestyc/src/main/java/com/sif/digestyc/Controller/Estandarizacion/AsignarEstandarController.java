package com.sif.digestyc.Controller.Estandarizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;
import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.ColumnaCorrelacionServiceImpl;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.EstandarServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.ColumnaVersionPlantillaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TipificacionServiceImpl;

@Controller
@RequestMapping("/asignarestandar")
public class AsignarEstandarController {
	private static final String cadenas = "string varchar char character";
	private static final String fecha = "datetime calendar";
	private static final String estandarCadena ="cadenas,fechas";
	private static final String estandarFecha ="fechas";
	
	private static final String INDEX="estandarizacion/asignarEstandar";
	private static final String ESTRUCTURA = "estandarizacion/estructura";
	
	private static final Logger LOG = LoggerFactory.getLogger(AsignarEstandarController.class);
	
	@Autowired
	private ColumnaCorrelacionServiceImpl columnaCorrelacionService;
	
	@Autowired
	private ColumnaVersionPlantillaServiceImpl columnaVersionService;
	
	@Autowired
	TablaServiceImpl tablaService;
	
	@Autowired
	private EstandarServiceImpl estandarService;
	
	@Autowired
	private TipificacionServiceImpl tipificacionService;

	@PreAuthorize("hasUrl('/asignarestandar/index')")
	@GetMapping("/index")
	public String getIndex(Model model,
			@RequestParam(name = "estandarNotFound", required = false) String notEstandar,
			@RequestParam(name = "success", required = false) String success) {
		model.addAttribute("columnasVersion", columnaVersionService.buscarTodas());
		model.addAttribute("estandares", estandarService.findAll());
		model.addAttribute("notArchivo", notEstandar);
		model.addAttribute("success", success);
		model.addAttribute("url", "/asignarestandar/index");
		return INDEX;
	}
	
	@PreAuthorize("hasUrl('/asignarestandar/guardar')")
	@GetMapping("/guardar")
	public String getGuardar() {
		return "redirect:/estandarizar/start_job";
	}

	@PreAuthorize("hasUrl('/asignarestandar/guardar')")
	@PostMapping("/guardar")
	public String guardar(Model model, HttpServletRequest request, @ModelAttribute("url") String url, @ModelAttribute("tabla_id") long tabla_id) {
		String redireccion = url.isEmpty()?"redirect:/asignarestandar/index":"redirect:"+url;
		List<String> errores = new ArrayList<>();
		if (request.getParameterValues("estandar") != null) {
			for (String t : request.getParameterValues("estandar")) {
				try {
					if (t.matches("[0-9]*,[0-9]*")) {
						int cId = Integer.parseInt(t.split(",")[0].trim());
						int tId = Integer.parseInt(t.split(",")[1].trim());
						Optional<ColumnaVersionPlantilla> pVerCol = columnaVersionService.buscarVersionColumna((long) cId);
						Optional<Estandar> estandar = estandarService.findById(tId);
						if (pVerCol.isPresent() && estandar.isPresent()) {
							ColumnaVersionPlantilla cvp = pVerCol.get();
							String tipoDato = cvp.getTipificacion().getVariacionTipoDato().getTipoDato().getTipoDatoJava();
							Estandar est = estandar.get();
							boolean isNumber = (!estandarCadena.contains(est.getGrupoDatos().toLowerCase()) && !cadenas.contains(tipoDato.toLowerCase()) && !fecha.contains(tipoDato.toLowerCase()));
							boolean isString = estandarCadena.contains(est.getGrupoDatos().toLowerCase());
							boolean isDate = estandarFecha.contains(est.getGrupoDatos().toLowerCase()) && fecha.contains(tipoDato.toLowerCase());
							boolean haveStandar = !est.getValoresTipicosEstandar().isEmpty() && !cvp.getTipificacion().getValorTipico().isEmpty();
							boolean haveValores = est.getValoresTipicosEstandar().isEmpty() == cvp.getTipificacion().getValorTipico().isEmpty() || isNumber || isDate;
							LOG.warn("es string = "+isString+" es numero = "+isNumber+!cadenas.contains(tipoDato) + !fecha.contains(tipoDato)+" dato a comparar = "+est.getGrupoDatos() + " - "+tipoDato);
							if((isString || isNumber || haveStandar) && haveValores) {
								ColumnaCorrespondencia columnaCorrespondencia = new ColumnaCorrespondencia();
								if(cvp.getColumnaCorrespondencia() != null) {
									columnaCorrespondencia = cvp.getColumnaCorrespondencia();
								}
								columnaCorrespondencia.setColumnaVersionPlantilla(cvp);
								columnaCorrespondencia.setEstandar(est);
								columnaCorrelacionService.actualizar(columnaCorrespondencia);
								cvp.setColumnaCorrespondencia(columnaCorrespondencia);
								columnaVersionService.actualizar(cvp);
							}else {
								String optional = haveValores?"":" debido a que uno tiene rango de valores y el otro no posee rangos";
								errores.add("Error en "+cvp.getPlantillaColumna().getCodigo()+" tipo de dato "+tipoDato+" no se podra parsear a "+est.getGrupoDatos()+optional);
							}
						}
					}
				}catch (Exception e) {
					redireccion += "?error=one_dat_no_saved";
					return redireccion;
				}
			}
			redireccion += "?success=insert";
		} else {
			redireccion += "?error=insert";
		}
		
		if(!errores.isEmpty()) {
			Optional<Tabla> t = tablaService.buscar(tabla_id);
			if(t.isPresent()) {
				Tabla tabla = t.get();
				if(tabla.getArchivo() != null) {
					if(tabla.getArchivo().getEntrega() != null && tabla.getArchivo().getEntrega().getVersionPlantilla() !=null) {
						model.addAttribute("columnasVersion", tabla.getArchivo().getEntrega().getVersionPlantilla().getVersionesColumna());
					}
				}
				model.addAttribute("estandares", estandarService.findAll());
				model.addAttribute("url", "/asignarestandar/"+tabla.getId()+"/editar/");
				model.addAttribute("tabla_id", tabla.getId());
				model.addAttribute("errores", errores);
			}
			redireccion = INDEX;
		}
		
		return redireccion;
	}
	
	
	@PreAuthorize("hasUrl('/asignarestandar/id/estructura')")
	@GetMapping("/{id}/estructura/")
	public String getEstructura(Model model, @PathVariable("id") Long id) {
		Optional<Tabla> t = tablaService.buscar(id);
		if(t.isPresent()) {
			Tabla tabla = t.get();
			if(tabla.getTablaCorrespondencia() != null) {
				model.addAttribute("tabla", tabla.getTablaCorrespondencia());
				List<ArrayList<String>> description = tablaService.getDescripcionTabla(tabla.getTablaCorrespondencia().getNombreTablaCorrespondiente());
				model.addAttribute("estructura", description);
			}
		}
		return ESTRUCTURA;
	}
	
	@PreAuthorize("hasUrl('/asignarestandar/{id}/editar/')")
	@GetMapping("/{id}/editar/")
	public String getEstandar(Model model, @PathVariable("id") Long id,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "error", required = false) String error) {
		Optional<Tabla> t = tablaService.buscar(id);
		if(t.isPresent()) {
			Tabla tabla = t.get();
			if(tabla.getArchivo() != null) {
				if(tabla.getArchivo().getEntrega() != null && tabla.getArchivo().getEntrega().getVersionPlantilla() !=null) {
					model.addAttribute("columnasVersion", tabla.getArchivo().getEntrega().getVersionPlantilla().getVersionesColumna());
				}
			}
			model.addAttribute("estandares", estandarService.findAll());
			model.addAttribute("url", "/asignarestandar/"+tabla.getId()+"/editar/");
			model.addAttribute("tabla_id", tabla.getId());
		}
		model.addAttribute("success", success);
		model.addAttribute("error", error);
		return INDEX;
	}
	
	

	
}
