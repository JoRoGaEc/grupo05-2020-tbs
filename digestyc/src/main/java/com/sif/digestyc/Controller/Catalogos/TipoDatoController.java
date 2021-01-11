package com.sif.digestyc.Controller.Catalogos;

import java.util.List;

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

import com.sif.digestyc.Entity.LoadModule.TipoDato;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Service.Load.TipoDatoService;
import com.sif.digestyc.Service.Load.VariacionTipoDatoService;

@Controller
@RequestMapping("/tipoDato")
public class TipoDatoController {
	private static final String FORM_LISTAR_TIPODATO = "catalogos/listadoTipoDato";
	private static final Logger LOG=  LoggerFactory.getLogger(TipoDatoController.class);
	private static final String VARIACIONES_POR_TIPO = "catalogos/variacionestipodato/variaciones";
	
	@Autowired
	@Qualifier("TipoDatoServiceImpl")
	private TipoDatoService tipoDatoService;
	
	@Autowired
	@Qualifier("variacionTipoDatoServiceImpl")
	private VariacionTipoDatoService variacionTipoDatoService;
	
	@PreAuthorize("hasUrl('/tipoDato/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoTipoDato(Model model, @RequestParam(name="error", required=false) String error, @RequestParam(name="success", required=false) String success) {
		ModelAndView  mav = new ModelAndView(FORM_LISTAR_TIPODATO);		
		mav.addObject("tipoDatos",tipoDatoService.listarTipoDato());	
		LOG.info("TOTAL REGISTROS RECUPERADOS " + tipoDatoService.listarTipoDato().size());
		mav.addObject("error", error);
	    mav.addObject("success", success);
		VariacionTipoDato varTipoDato  = new VariacionTipoDato();
		model.addAttribute("varTipoDato", varTipoDato);
		return mav;
	}

	@PreAuthorize("hasUrl('/tipoDato/addTipoDato')")	
	@PostMapping("/addVariacionTipo")
	public String agregarTipoDato(@Valid @ModelAttribute(name="varTipoDato") VariacionTipoDato varTipoDato, BindingResult bindinResult, @ModelAttribute(name="id_tipo_dato_1") long idTipoDato, Model model)
	{
		
		LOG.info("ESTE ES EL ID DEL TIPO DE DATO" + idTipoDato);
		String redireccion = "redirect:/tipoDato/listar";
		TipoDato td = tipoDatoService.buscarTipoDatoPorId(idTipoDato);
		if (!bindinResult.hasErrors() && (varTipoDato != null) &&   (td != null)) {
			varTipoDato.setTipoDato(td);
			td.addVariacionTipo(varTipoDato);
			variacionTipoDatoService.saveVariacionTipoDato(varTipoDato);
			redireccion = redireccion + "?success=insert";
		} else {
			redireccion = redireccion + "?error=insert";
		}
		return redireccion;
		
	}
	
	//@PreAuthorize("hasUrl('/tipoDato/addTipoFecha')")	 pendiente
	@PostMapping("/addVariacionTipoFecha")
	public String agregarTipoFecha(@Valid @ModelAttribute(name="varTipoDato") VariacionTipoDato varTipoDato, BindingResult bindinResult, @ModelAttribute(name="id_tipo_dato_2") long idTipoDato, Model model)
	{
		
		LOG.info("ESTE ES EL ID DEL TIPO DE DATO" + idTipoDato);
		String redireccion = "redirect:/tipoDato/listar";
		TipoDato td = tipoDatoService.buscarTipoDatoPorId(idTipoDato);
		if (!bindinResult.hasErrors() && (varTipoDato != null) &&   (td != null)) {
			varTipoDato.setTipoDato(td);
			td.addVariacionTipo(varTipoDato);
			variacionTipoDatoService.saveVariacionTipoDato(varTipoDato);
			redireccion = redireccion + "?success=insert";
		} else {
			redireccion = redireccion + "?error=insert";
		}
		return redireccion;
		
	}
	@PostMapping("/addVariacionDecimales")
	public String agregarVariacionDecimales(@Valid @ModelAttribute(name="varTipoDato") VariacionTipoDato varTipoDato, BindingResult bindinResult, @ModelAttribute(name="id_tipo_dato_3") long idTipoDato, Model model)
	{
		
		LOG.info("ESTE ES EL ID DEL TIPO DE DATO" + idTipoDato);
		String redireccion = "redirect:/tipoDato/listar";
		TipoDato td = tipoDatoService.buscarTipoDatoPorId(idTipoDato);
		if (!bindinResult.hasErrors() && (varTipoDato != null) &&   (td != null)) {
			varTipoDato.setTipoDato(td);
			td.addVariacionTipo(varTipoDato);
			variacionTipoDatoService.saveVariacionTipoDato(varTipoDato);
			redireccion = redireccion + "?success=insert";
		} else {
			redireccion = redireccion + "?error=insert";
		}
		return redireccion;
		
	}
	
	@GetMapping("/variaciones/{id}")
	public ModelAndView variacionesPorTipo(@PathVariable(name="id") Long id) {
		List<VariacionTipoDato> variaciones = variacionTipoDatoService.variacionPorTipo(id);		
		ModelAndView mav =  new ModelAndView(VARIACIONES_POR_TIPO);
		mav.addObject("variaciones",variaciones);
		mav.addObject("tipoDato", tipoDatoService.buscarTipoDatoPorId(id));
		return mav;
	}
	
	@RequestMapping("/removervariacion")
	public String removerVariacion(@ModelAttribute(name="id_variacion") Long id) {
		String redireccion  = "redirect:/tipoDato/listar";
		LOG.info("ID VARIACION : " + id);
		try {
			VariacionTipoDato variacionDb  = variacionTipoDatoService.buscarVariacionPorId(id).get();
			if(variacionDb !=null) {
				variacionTipoDatoService.delete(variacionDb);
			}
			redireccion = redireccion + "?success=delete";
		}catch(Exception e) {
			redireccion = redireccion + "?error=delete";
		}
		return redireccion;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
