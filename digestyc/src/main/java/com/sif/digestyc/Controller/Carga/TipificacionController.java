package com.sif.digestyc.Controller.Carga;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Service.Load.VariacionTipoDatoService;
import com.sif.digestyc.Service.Load.CargaImpl.TipificacionServiceImpl;
import com.sif.digestyc.Service.Load.CatalogosImpl.InstitucionServiceImpl;
import com.sif.digestyc.Service.Load.CatalogosImpl.TipoDatoServiceImpl;
import com.sif.digestyc.Service.Load.CatalogosImpl.ValorTipicoServiceImpl;

@Controller
@RequestMapping("/tipificacion")
public class TipificacionController {
	private static final String LISTAR = "carga/tipificacion/listar";
	private static final String AGREGAR = "carga/tipificacion/agregar";
	private static final String NUEVO = "carga/tipificacion/nuevo";
	private static final String EDITAR = "carga/tipificacion/editar";
	private static final int NUMERO = 1;
	private static final int VARCHAR = 2;
	private static final int FECHA = 3;
	private static final int TODO = 100;
	
	private static final Logger LOG=  LoggerFactory.getLogger(TipificacionController.class);

	@Autowired
	private TipificacionServiceImpl tipificacionService;

	@Autowired
	private TipoDatoServiceImpl tipoDatoService;

	@Autowired
	private InstitucionServiceImpl institucionService;

	@Autowired
	private ValorTipicoServiceImpl valorTipicoService;
	
	@Autowired
	@Qualifier("variacionTipoDatoServiceImpl")
	private VariacionTipoDatoService variacionTipoDatoService;
	

	
	@PreAuthorize("hasUrl('/tipificacion/nuevo')")
	@GetMapping("/nuevo")
	public String nuevaTipificacion(Model model, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("tiposDatos", tipoDatoService.listarTipoDato());
		model.addAttribute("tipificacion", new Tipificacion());
		return NUEVO;
	}
	
	
	//pendiente
	@RequestMapping(value = "/tiposespecificos",method =  RequestMethod.GET)
	public ResponseEntity<?> getSearchResultViaAjax(@RequestParam Long idTipoDato) {
        List<VariacionTipoDato>  result =  variacionTipoDatoService.variacionPorTipoParaTipificacion(idTipoDato);
        LOG.info("ID DE EL TIPO " + idTipoDato);
        LOG.info("TAMAÑO: " + result.size());
		return ResponseEntity.ok(result);
	}	

	
	@PreAuthorize("hasUrl('/tipificacion/listar')")
	@GetMapping("/listar")
	public String getIndex(Model model, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("tipificaciones", tipificacionService.buscarTodo());
		model.addAttribute("tiposDatos", tipoDatoService.listarTipoDato());
		return LISTAR;
	}

	
	@PreAuthorize("hasUrl('/tipificacion/agregar')")
	@PostMapping("/agregar")
	public ModelAndView agregarTipificacion(@Valid @ModelAttribute("tipificacion") Tipificacion tipificacion, BindingResult result, @ModelAttribute("dato_id") String dato_id,
											@ModelAttribute("dato_var_id") String dato_var_id,
											@ModelAttribute("rango") String rango, 
											@ModelAttribute("inicio_numero") String inicioNumero, 
											@ModelAttribute("fin_numero") String finNumero, 
											@ModelAttribute("valores") String valores,
											@ModelAttribute("inicio_fecha") String inicioFecha, 
											@ModelAttribute("fin_fecha") String finFecha) throws ParseException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("tiposDatos", tipoDatoService.listarTipoDato());
		
		if(result.hasErrors()) {
			mav.addObject("tiposDatos", tipoDatoService.listarTipoDato());
			mav.addObject("tipificacion", new Tipificacion());
			mav.setViewName(NUEVO);
			mav.addObject("error", "insert");
			System.out.println(result.getAllErrors());
			return mav;
		}
		
		
		mav.setViewName("redirect:/tipificacion/nuevo");
		if(!dato_var_id.isEmpty() && dato_var_id.matches("[0-9]*") && !result.hasErrors()){
			if(!dato_id.isEmpty() && dato_id.matches("[0-9]*") && !result.hasErrors()) {
				Optional<VariacionTipoDato> dato = variacionTipoDatoService.buscarVariacionPorId(Long.parseLong(dato_var_id));
				LOG.info("ID VAR ESPECIFICO " + dato_var_id);
				//VariacionTipoDato dato = tipoDatoService.buscarTipoDatoPorId(Long.parseLong(dato_id));
				tipificacion.setVariacionTipoDato(dato.get());
				tipificacion.setNombre(tipificacion.getNombre().trim().toUpperCase());
				if(!rango.isEmpty() && rango.matches("[1-3]*")) { //Validamos solo hasta el rango 3, pues son los unicos que pueden tener rango de valores 
					int r = Integer.parseInt(rango); //No pongo una excepcion, ya que es imposible que haya una excepcion en "teoria"
					switch (r) {
					case NUMERO:
						if(tipificacionService.esRangoNumeroValido(r, inicioNumero, finNumero, dato.get())) {
							LOG.info("ES TIPO NUMERO");
							tipificacion = tipificacionService.actualizarTipificacion(tipificacion);
							ValorTipico valor = new ValorTipico(tipificacion, NUMERO, (float)Double.parseDouble(inicioNumero), (float)Double.parseDouble(finNumero));
							valorTipicoService.guardarValor(valor);
							mav.addObject("success", "insert");
						}else {
							mav.addObject("error", "RANGONUMERO");
							mav.setViewName("redirect:/tipificacion/nuevo");
						}
						break;
					case VARCHAR:
						if(tipificacionService.esRangoValido(r, valores, dato.get())) {
							//Guardamos los datos de un varchar
							String todos[] = valores.split(",");
							tipificacion = tipificacionService.actualizarTipificacion(tipificacion);
							for(String v : todos) {
								ValorTipico valor = new ValorTipico(tipificacion, VARCHAR, v.trim());
								valorTipicoService.guardarValor(valor);
							}
							mav.addObject("success", "insert");
						}else {
							mav.addObject("error", "VALORES");
						}					
						break;
					case 3:
						if(tipificacionService.esRangoFechaValido(r, inicioFecha, finFecha, dato.get())) {
							//guardamos datos de fechas
							SimpleDateFormat formatos = new SimpleDateFormat("yyyy-MM-dd");
							Date f1 = formatos.parse(inicioFecha);
							Date f2 = formatos.parse(finFecha);
							System.out.println("F1: "+f1.toString()+" F2:"+f2.toString());
							tipificacion = tipificacionService.actualizarTipificacion(tipificacion);
							ValorTipico valor = new ValorTipico(tipificacion, FECHA, f1, f2);
							valorTipicoService.guardarValor(valor);
							mav.addObject("success", "insert");
						}else {
							mav.addObject("error", "RANGOFECHA");
						}
						break;
					}	
				}else {
					tipificacion = tipificacionService.actualizarTipificacion(tipificacion);
					mav.addObject("success", "insert");
				}
			}else {
				mav.addObject("error", "DATO");
				mav.addObject("error", "insert");
			}				
		}else {
			mav.addObject("error", "VARIACION");
			mav.addObject("error", "insert");
		}

		return mav;
	}


	@PreAuthorize("hasUrl('/tipificacion/editar')")
	@GetMapping("/editar/{id}/")
	public ModelAndView editarTipificacion(Model model, @PathVariable("id") int id, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		ModelAndView mav = new ModelAndView(EDITAR);
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("tiposDatos", tipoDatoService.listarTipoDato());
		Tipificacion tipificacion = tipificacionService.buscarPorId(id);
		mav.addObject("tipificacion", tipificacion);
		mav.addObject("full", !tipificacion.getValorTipico().isEmpty());
		return mav;
	}


	@PreAuthorize("hasUrl('/tipificacion/actualizar')")
	@PostMapping("/actualizar")
	public ModelAndView actualizarTipificacion(@Valid @ModelAttribute("tipificacion") Tipificacion tipificacion, BindingResult result, @ModelAttribute("dato_id") String dato_id, 
												@ModelAttribute("dato_var_id") String dato_var_id,
												@ModelAttribute("rango") String rango, @ModelAttribute("inicio_numero") String inicioNumero, 
												@ModelAttribute("fin_numero") String finNumero, @ModelAttribute("valores") String valores,
												@ModelAttribute("inicio_fecha") String inicioFecha, @ModelAttribute("fin_fecha") String finFecha) throws ParseException {
		ModelAndView mav = new ModelAndView();
		mav.addObject("tiposDatos", tipoDatoService.listarTipoDato());
		mav.setViewName("redirect:/tipificacion/editar/"+tipificacion.getId()+"/");
		
		if(!result.hasErrors() && !dato_id.isEmpty() && dato_id.matches("[0-9]*") ) {
			if(!dato_var_id.isEmpty() && dato_id.matches("[0-9]*") && !result.hasErrors()) {
				    Optional<VariacionTipoDato> dato = variacionTipoDatoService.buscarVariacionPorId(Long.parseLong(dato_var_id));
					Tipificacion tipificacionActualizar = tipificacionService.buscarPorId(tipificacion.getId().intValue());
					tipificacionActualizar.setNombre(tipificacion.getNombre().trim().toUpperCase());
					tipificacionActualizar.setEsNulo(tipificacion.isEsNulo());
					tipificacionActualizar.setDescripcion(tipificacion.getDescripcion());
					tipificacionActualizar.setVariacionTipoDato(dato.get());
					tipificacion = tipificacionService.actualizarTipificacion(tipificacionActualizar);
					mav.addObject("full", !tipificacion.getValorTipico().isEmpty());
					if(tipificacion.getValorTipico().size()<1 && !rango.isEmpty() && rango.matches("[1-3]*")) { //Validamos solo hasta el rango 3, pues son los unicos que pueden tener rango de valores 
						int r = Integer.parseInt(rango); //No pongo una excepcion, ya que es imposible que haya una excepcion en "teoria"
						switch (r) {
						case NUMERO:
							if(tipificacionService.esRangoNumeroValido(r, inicioNumero, finNumero, dato.get())) {
								//Guardamos lo correspondiente a numeros
								ValorTipico valor = new ValorTipico(tipificacion, NUMERO, (float)Double.parseDouble(inicioNumero), (float)Double.parseDouble(finNumero));
								valorTipicoService.guardarValor(valor);
								mav.addObject("success", "editValores");
							}else {
								mav.addObject("error", "RANGONUMERO");
								mav.setViewName("redirect:/tipificacion/editar/"+tipificacion.getId()+"/");
							}
							break;
						case VARCHAR:
							if(tipificacionService.esRangoValido(r, valores, dato.get())) {
								//Guardamos los datos de un varchar
								String todos[] = valores.split(",");
								for(String v : todos) {
									ValorTipico valor = new ValorTipico(tipificacion, VARCHAR, v.trim());
									valorTipicoService.guardarValor(valor);
									mav.addObject("success", "editValores");
								}
							}else {
								mav.addObject("error", "VALORES");
								mav.setViewName("redirect:/tipificacion/editar/"+tipificacion.getId()+"/");
							}					
							break;
						case FECHA:
							if(tipificacionService.esRangoFechaValido(r, inicioFecha, finFecha, dato.get())) {
								//guardamos datos de fechas
								SimpleDateFormat formatos = new SimpleDateFormat("yyyy-MM-dd");
								Date f1 = formatos.parse(inicioFecha);
								Date f2 = formatos.parse(finFecha);
								System.out.println("F1: "+f1.toString()+" F2:"+f2.toString());
								ValorTipico valor = new ValorTipico(tipificacion, FECHA, f1, f2);
								valorTipicoService.guardarValor(valor);
								mav.addObject("success", "editValores");
							}else {
								mav.addObject("error", "RANGOFECHA");
								mav.setViewName("redirect:/tipificacion/editar/"+tipificacion.getId()+"/");
							}
							break;
						}	
					}	
				}else {
					mav.addObject("error", "update");
				}
		}else {
			System.out.println(tipificacion.getDescripcion());
			if(!tipificacion.getDescripcion().trim().isEmpty() && !result.hasErrors()) {
				Tipificacion tipificacionActualizar = tipificacionService.buscarPorId(tipificacion.getId().intValue());
				tipificacionActualizar.setDescripcion(tipificacion.getDescripcion());
				tipificacionService.actualizarTipificacion(tipificacionActualizar);
				mav.addObject("success", "edit");
			}else {
				mav.addObject("tiposDatos", tipoDatoService.listarTipoDato());
				Tipificacion tipificacionActualizar = tipificacionService.buscarPorId(tipificacion.getId().intValue());
				mav.addObject("tipificacion", tipificacionActualizar);
				mav.addObject("full", !tipificacionActualizar.getValorTipico().isEmpty());
				mav.setViewName(EDITAR);
				mav.addObject("error", "edit");
				System.out.println(result.getAllErrors());
				return mav;
			}
		}
		
		return mav;
	}


	@PreAuthorize("hasUrl('/tipificacion/eliminarRango')")
	@PostMapping("/eliminarRango")
	public String eliminarRango(@ModelAttribute("id") int id) {
		String valor = "redirect:/tipificacion/editar/"+id+"/";
		Tipificacion tipificacion = tipificacionService.buscarPorId(id);
		try {
		for (ValorTipico valorTipico : tipificacion.getValorTipico()) {
			valorTipicoService.eliminarValorTipico(valorTipico);
		}
		} catch (Exception e) {
			valor = valor + "?error=delete";
		}
		return valor;
	}


	@PreAuthorize("hasUrl('/tipificacion/remover')")
	@PostMapping("/remover")
	public String removerTipificacion(@ModelAttribute("id_eliminar") int id) {
		String retorno = "redirect:/tipificacion/listar";
		try {
			Tipificacion tipificacion = tipificacionService.buscarPorId(id);
			if (tipificacion!=null) {
			tipificacionService.eliminarTipificacion(tipificacion);
		}
			retorno = retorno + "?success=delete";
		}catch (Exception e) {
			retorno = retorno + "?error=delete";
		}
		return retorno;
	}


	@PreAuthorize("hasUrl('/tipificacion/agregarTipificaciones')")
	@GetMapping("/agregarTipificaciones/{id}/")
	public String agregarTipificaciones(Model model,@PathVariable("id") Long id, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("tipificaciones", tipificacionService.buscarTodoMenos(id.intValue()));
		model.addAttribute("institucion", institucionService.buscarInstitucionPorId(id));
		model.addAttribute("regresar", "/instituciones/listar");
		return AGREGAR;
	}
	
	@PreAuthorize("hasUrl('/tipificacion/agregarTipificaciones')")
	@GetMapping("/desdeTipificaciones/{id}/")
	public String agregarTipificacionesDesdeT(Model model,@PathVariable("id") Long id, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("tipificaciones", tipificacionService.buscarTodoMenos(id.intValue()));
		model.addAttribute("institucion", institucionService.buscarInstitucionPorId(id)); 
		model.addAttribute("regresar", "/registro/listar");
		return AGREGAR;
	}

	
	@PreAuthorize("hasUrl('/tipificacion/eliminarTipificacion')")
	@PostMapping("/eliminarTipificacion")
	public String eliminarTipificacion(Model model, @ModelAttribute(name="institucion_id") Long id, @ModelAttribute(name="eliminar_todo") int elimnarTodo, HttpServletRequest request, @ModelAttribute(name="regresar") String regresar) {
		Institucion institucion = institucionService.buscarInstitucionPorId(id);
		List<Tipificacion> tipificaciones = institucion.getTipificaciones();
		if(elimnarTodo == TODO) {
			tipificaciones.clear();
		}
		else {
			if(elimnarTodo == 0 && request.getParameterValues("quitarTipificacion")!=null) {
				for(String tip_id : request.getParameterValues("quitarTipificacion")) {
					int id_tip = Integer.parseInt(tip_id);
					tipificaciones.remove(tipificacionService.buscarPorId(id_tip));
				}
			}
		}
		institucionService.actualizadInstitucion(institucion);
		String redireccion = "";
		if(regresar.contains("instituciones/listar")) {
			redireccion = "redirect:/tipificacion/agregarTipificaciones/"+id+"/";
		}else {
			redireccion = "redirect:/tipificacion/desdeTipificaciones/"+id+"/";
		}
		return redireccion;
	}
	
	
	@PreAuthorize("hasUrl('/tipificacion/guardarTipificacion')")
	@PostMapping("/guardarTipificacion")
	public String guardarTipificacion(Model model, @ModelAttribute(name="institucion_id") String id, @ModelAttribute(name="agregarTodoTipificacion") int agregarTodoPermiso, HttpServletRequest request, @ModelAttribute(name="regresar") String regresar) {		
		Institucion institucion = institucionService.buscarInstitucionPorId(Long.parseLong(id));
		if(agregarTodoPermiso == TODO) {
			institucion.setTipificaciones(tipificacionService.buscarTodo());
		}else {
			if (agregarTodoPermiso == 0 && request.getParameterValues("guardarTipificacion") != null) {
				for(String tipificacion_id : request.getParameterValues("guardarTipificacion")) {
					int tip_id = Integer.parseInt(tipificacion_id);
					institucion.setTipificaciones(tipificacionService.buscarPorId(tip_id));
				}
			}
		}
		institucionService.actualizadInstitucion(institucion);
		String redireccion = "";
		if(regresar.contains("instituciones/listar")) {
			redireccion = "redirect:/tipificacion/agregarTipificaciones/"+id+"/";
		}else {
			redireccion = "redirect:/tipificacion/desdeTipificaciones/"+id+"/";
		}
		return redireccion;
	}
}