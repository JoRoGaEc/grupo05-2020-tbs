package com.sif.digestyc.Controller.Estandarizacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.ValorTipico;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Entity.StandardizationModule.ColumnaCorrespondencia;
import com.sif.digestyc.Entity.StandardizationModule.Estandar;
import com.sif.digestyc.Entity.StandardizationModule.ValorTipicoEstandar;
import com.sif.digestyc.Service.Estandarizacion.CorrespondenciaService;
import com.sif.digestyc.Service.Estandarizacion.IEstandarService;
import com.sif.digestyc.Service.Estandarizacion.TipoDatoEstandarService;
import com.sif.digestyc.Service.Estandarizacion.TipoDatoSQLServerService;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.ValorTipicoEstandarServiceImpl;
import com.sif.digestyc.Service.Load.ColumnaVersionPlantillaService;

@Controller
@RequestMapping("/estandares")
public class EstandarController {

	private static final Logger LOG = LoggerFactory.getLogger(EstandarController.class);
	private static final String FORM_EMPAREJAR_ESTANDARES = "estandarizacion/emparejar-estandares";
	public static final String NUEVO_ESTANDAR = "estandarizacion/estandar/nuevo";
	public static final String EDITAR_ESTANDAR = "estandarizacion/estandar/editar";
	public static final String FORM_LISTAR_ESTANDAR = "estandarizacion/estandar/listadoEstandar";

	@Autowired
	@Qualifier("tipoDatoSQLServerImpl")
	private TipoDatoSQLServerService tipoDatoEstandarService;

	@Autowired
	@Qualifier("estandarServiceImpl")
	private IEstandarService estandarServiceImpl;

	@Autowired
	@Qualifier("columnaVersionPlantillaServiceImpl")
	private ColumnaVersionPlantillaService columnaVersionService;

	@Autowired
	@Qualifier("estandarServiceImpl")
	private IEstandarService estandarService;

	@Autowired
	@Qualifier("valorTipicoEstandarServiceImpl")
	private ValorTipicoEstandarServiceImpl ValorTipicoEstandarService;

	@Autowired
	@Qualifier("correspondenciaServiceImpl")
	private CorrespondenciaService correspondenciaService;

	// indexEstandar
	@PreAuthorize("hasUrl('/estandares/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoRegistro(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success) {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_ESTANDAR);
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		mav.addObject("estandares", estandarService.findAll());
		Estandar estandar = new Estandar();
		model.addAttribute("estandarObject", estandar);
		return mav;
	}

	@PreAuthorize("hasUrl('/estandares/form')")
	@GetMapping("/form") // {id} es el id de la tipificacion
	public String nuevoEstandar(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success) {
		LOG.info("** nuevoEstandar ");
		LOG.info("** Longitud " + tipoDatoEstandarService.listarTipoDeDatosEstandar().size());
		model.addAttribute("error", error);
		model.addAttribute("success", success);
		model.addAttribute("tiposDatosEstandar", tipoDatoEstandarService.listarTipoDeDatosEstandar());
		model.addAttribute("estandar", new Estandar());
		model.addAttribute("datos_val_tipicos", "");

		return NUEVO_ESTANDAR;

	}

	@PreAuthorize("hasUrl('/estandares/nuevo/guardar')")
	@PostMapping("/nuevo/guardar")
	public ModelAndView guardarEstandar(@ModelAttribute(name = "datos_val_tipicos") String datosValoresTipicos,
			@Valid @ModelAttribute(name = "estandar") Estandar estandar, BindingResult result,
			HttpServletRequest request) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.addObject("tiposDatosEstandar", tipoDatoEstandarService.listarTipoDeDatosEstandar());
			mav.addObject("estandar", new Estandar());
			mav.setViewName(NUEVO_ESTANDAR);
			mav.addObject("error", "insert");
			System.out.println(result.getAllErrors());
			return mav;
		}

		LOG.info("**guardarEstandar method():" + datosValoresTipicos);
		List<Map<String, Object>> elementos = new ArrayList<>();
		Map<String, Object> datos = new HashMap<>();
		String valorTipicoEstandar = "";
		String descrValTipEstandar = "";
		String[] valores;
		if (datosValoresTipicos != null) {
			valores = datosValoresTipicos.split(",");
			for (String val : valores) {
				if (val.startsWith("f_")) {
					datos = new HashMap<>();
					String numeroString = val.replace("f_", "");
					Integer numero = Integer.parseInt(numeroString);
					valorTipicoEstandar = request.getParameter("valtip_" + numero);
					descrValTipEstandar = request.getParameter("destip_" + numero);
					datos.put("valorTipico", valorTipicoEstandar);
					datos.put("descripci", descrValTipEstandar);
					elementos.add(datos);
				}
			}
			estandarServiceImpl.guardarEstandarConValoresTipicos(estandar, elementos);
			mav.addObject("success", "insert");
		}

		mav.setViewName("redirect:/estandares/form" + "/");
		return mav;
	}

	@PreAuthorize("hasUrl('/estandares/editar')")
	@GetMapping("/editar/{id}/")
	public ModelAndView editarEstandar(Model model, @PathVariable("id") int id,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success) {
		ModelAndView mav = new ModelAndView(EDITAR_ESTANDAR);
		mav.addObject("error", error);
		mav.addObject("success", success);
		mav.addObject("tiposDatosEstandar", tipoDatoEstandarService.listarTipoDeDatosEstandar());
		Optional<Estandar> estandar = estandarService.findById(id);
		mav.addObject("estandar", estandar.get());
		mav.addObject("full", !estandar.get().getValoresTipicosEstandar().isEmpty());
		return mav;
	}

	@PreAuthorize("hasUrl('/estandares/actualizar')")
	@PostMapping("/actualizar")
	public ModelAndView actualizarEstandar(@Valid @ModelAttribute("estandar") Estandar estandar,BindingResult result,
			@ModelAttribute(name = "datos_val_tipicos") String datosValoresTipicos, HttpServletRequest request)throws ParseException {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/estandares/editar/" + estandar.getId() + "/");
		
		Optional<Estandar> estandare = estandarService.findById(estandar.getId().intValue());
		try {
			if (!result.hasErrors()) {
				Estandar estandaractualizar = estandare.get();
				estandaractualizar.setNombre(estandar.getNombre().trim().toUpperCase());
				estandaractualizar.setEsVacio(estandar.getEsVacio());
				estandaractualizar.setDescripcion(estandar.getDescripcion());
				estandaractualizar.setGrupoDatos(estandar.getGrupoDatos());
				estandaractualizar.setLongitudN(estandar.getLongitudN());
				estandaractualizar.setEscala(estandar.getEscala());
				estandaractualizar.setPrecision(estandar.getPrecision());
				estandaractualizar.setTipoDato(estandar.getTipoDato());
				estandar = estandarService.actualizarEstandar(estandaractualizar);

				mav.addObject("full", !estandare.get().getValoresTipicosEstandar().isEmpty());
				
				//Falta agregar los valores en esta parte
			}
		} catch (Exception e) {
			mav.addObject("tiposDatosEstandar", tipoDatoEstandarService.listarTipoDeDatosEstandar());
			mav.setViewName(EDITAR_ESTANDAR);
			mav.addObject("error", "edit");
			System.out.println(result.getAllErrors());
			return mav;
		}
		return mav;
	}

	@PreAuthorize("hasUrl('/estandares/eliminarRango')")
	@PostMapping("/eliminarRango")
	public String eliminarRango(@ModelAttribute("id") int id) {
		String valor = "redirect:/estandares/editar/" + id + "/";
		LOG.info("HOLA");
		Optional<Estandar> estandar = estandarService.findById(id);
		LOG.info("Id Estandar" + estandar.get().getId());
		try {
			for (ValorTipicoEstandar valorTipico : estandar.get().getValoresTipicosEstandar()) {
				LOG.info("Valores" + valorTipico.getValorTipico() + valorTipico.getId());
				ValorTipicoEstandarService.eliminarValorEstandar(valorTipico);
			}
		} catch (Exception e) {
			valor = valor + "?error=delete";
		}
		return valor;
	}

	@PreAuthorize("hasUrl('/estandares/remover')")
	@PostMapping("/remover")
	public String removerEstandar(@ModelAttribute("id_eliminar") int id) {
		String retorno = "redirect:/estandares/listar";
		try {
			Optional<Estandar> estandar = estandarService.findById(id);
			if (estandar != null) {
				estandarService.eliminarEstandar(estandar.get());
			}
			retorno = retorno + "?success=delete";
		} catch (Exception e) {
			retorno = retorno + "?error=delete";
		}
		return retorno;
	}

	@PreAuthorize("hasUrl('/estandares/asignar/valores-tipicos/{id}/{idEstandar}/{IdTabla}/editar/')")
	@GetMapping("/asignar/valores-tipicos/{id}/{idEstandar}/{IdTabla}/editar/") /* id -> idVersionPlantilla */
	public ModelAndView asignarValoresTipicosTabla(@PathVariable(name = "id") Long id,
			@PathVariable(name = "idEstandar") Long idEstandar, @PathVariable(name = "IdTabla") Long IdTabla) {
		ModelAndView mav = new ModelAndView(FORM_EMPAREJAR_ESTANDARES);
		Optional<ColumnaVersionPlantilla> cvp = Optional.of(new ColumnaVersionPlantilla());
		cvp = columnaVersionService.buscarVersionColumna(id);
		mav.addObject("versionColumna", cvp.get());
		mav.addObject("estandar", estandarService.findById(idEstandar).get());
		mav.addObject("url", "/asignarestandar/" + IdTabla + "/editar/");
		return mav;

	}

	@PreAuthorize("hasUrl('/emparejar-valores/guardar')")
	@PostMapping("/emparejar-valores/guardar")
	public String guardar(Model model, HttpServletRequest request, @ModelAttribute("url") String url) {
		LOG.info("HOLA");
		List<Map<String, Object>> datos = new ArrayList<>();
		Map<String, Object> registro = new HashMap<>();
		String redireccion = url == null || url.isEmpty() ? "redirect:/asignarestandar/index" : "redirect:" + url;
		if (request.getParameterValues("valorEstandar") != null) {
			for (String t : request.getParameterValues("valorEstandar")) {
				registro = new HashMap<>();
				try {
					if (t.matches("[0-9]*,[0-9]*")) {
						int valorT = Integer.parseInt(t.split(",")[0].trim());// valorTipico de la columna
						int valorTEstandar = Integer.parseInt(t.split(",")[1].trim());// valorTipico del estandar
						registro.put("valorT", valorT);
						registro.put("valorTEstandar", valorTEstandar);
					}
				} catch (Exception e) {
					redireccion += "?error=unknow";
					return redireccion;
				}
				datos.add(registro);

			}
			try {
				correspondenciaService.guardarCorrespondencias(datos);
				redireccion += "?success=insert";
			} catch (Exception e) {
				e.printStackTrace();
				redireccion += "?errorNoSave=true";
			}
		} else {
			redireccion += "?error=insert";
		}
		return redireccion;
	}

	@GetMapping("/asignar/valores-tipicos/{id}/{idEstandar}") /* id -> idVersionPlantilla */
	public ModelAndView asignarValoresTipicos(@PathVariable(name = "id") Long id,
			@PathVariable(name = "idEstandar") Long idEstandar) {
		ModelAndView mav = new ModelAndView(FORM_EMPAREJAR_ESTANDARES);
		Optional<ColumnaVersionPlantilla> cvp = Optional.of(new ColumnaVersionPlantilla());
		cvp = columnaVersionService.buscarVersionColumna(id);
		mav.addObject("versionColumna", cvp.get());
		mav.addObject("estandar", estandarService.findById(idEstandar).get());
		mav.addObject("url", "/asignarestandar/index");
		return mav;

	}

}
