package com.sif.digestyc.Controller.Carga;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.TipoRegistro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Service.Load.InstitucionService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;
import com.sif.digestyc.Service.Load.CargaImpl.CrearRegistroServiceImpl;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/plantillas")
public class PlantillaController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PlantillaController.class);
	
	public static final String INSTITUCIONES_REGISTROS = "/carga/plantillas/instituciones-ra-u-oe";
	public static final String RA_POR_INSTITUCION = "/carga/plantillas/ra-por-institucion";
	public static final String OE_POR_INSTITUCION = "/carga/plantillas/oe-por-institucion";
	public static final String GESTIONAR_PLANTILLAS = "/carga/plantillas/gestionar-plantillas";
	public static final String GESTIONAR_COLUMNASVP = "/carga/plantillas/columnas-vp";
	
	@Autowired
	@Qualifier("institucionServiceImpl")
	private InstitucionService institucionServiceImpl;

	@Autowired
	@Qualifier("crearRegistroService")
	private CrearRegistroServiceImpl registroService;

	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;
	
	@PreAuthorize("hasUrl('/plantillas/instituciones-con-ra-u-oe')")
	@GetMapping("/instituciones-con-ra-u-oe")
	public ModelAndView listarInstituciones() {
	System.out.println("Estoy dentro del listarInstituciones");
	ModelAndView mav =  new ModelAndView();
	List<Institucion> institucionesConRegistros = institucionServiceImpl.listarInstituciones()
			.stream()
			.filter(i -> i.getRegistros().size() > 0) /*Aca va realizar consulta en demanda*/
			.collect(Collectors.toList());
	 mav.setViewName(INSTITUCIONES_REGISTROS);
	 mav.addObject("instituciones", institucionesConRegistros);
	 return mav;
	}
	
	@PreAuthorize("hasUrl('/plantillas/ra/{id}/')")
	@GetMapping("/ra/{id}/")
	public ModelAndView registrosAdministrativosInstitucion(@PathVariable("id") Long id,
			@RequestParam(name = "success", required = false) String insert) {
		Boolean hasElementos = false;
		ModelAndView mav=  new ModelAndView();
		Institucion institucion = new Institucion();
		mav.setViewName(RA_POR_INSTITUCION);
		List<Registro>  raPorInstitucion = (List<Registro>) institucionServiceImpl.registroAdministraticoByInstitucion(id, 1L);
		mav.addObject("radministrativos", raPorInstitucion);	
		if(raPorInstitucion.size() > 0) {
			institucion =  raPorInstitucion.get(0).getInstitucion();
			mav.addObject("institucion", institucion);
			hasElementos =  true;
		}	
		mav.addObject("hasElementos", hasElementos);
		mav.addObject("success", insert); //Msj version plantilla
		return mav;
		
	}
	
	@PreAuthorize("hasUrl('/plantillas/oe/{id}/')")
	@GetMapping("/oe/{id}/")
	public ModelAndView operacionesEstadisticasPorInstitucion(@PathVariable("id") Long id,
			@RequestParam(name = "success", required = false) String insert) {
		Boolean hasElementos = false;
		ModelAndView mav=  new ModelAndView();
		Institucion institucion = new Institucion();
		mav.setViewName(OE_POR_INSTITUCION);
		List<Registro>  raPorInstitucion = (List<Registro>) institucionServiceImpl.registroAdministraticoByInstitucion(id, 2L);
		mav.addObject("radministrativos", raPorInstitucion);	
		if(raPorInstitucion.size() > 0) {
			institucion =  raPorInstitucion.get(0).getInstitucion();
			mav.addObject("institucion", institucion);
			hasElementos =  true;
		}
		mav.addObject("hasElementos", hasElementos);
		mav.addObject("success", insert); //Msj version plantilla
		return mav;
		
	}	
	
	@PreAuthorize("hasUrl('/plantillas/gestionar-plantillas/{id}/')")
	@GetMapping("/gestionar-plantillas/{id}/")
	public ModelAndView gestionarPlantilasDeRegistro(@PathVariable("id") Long id,
													@RequestParam(name = "success", required = false) String success) {
		ModelAndView mav  = new ModelAndView(GESTIONAR_PLANTILLAS);
		Optional<Registro> registro = registroService.buscarPorId(id);
		Registro registroDb  = new Registro();
		Plantilla plantilla =  new Plantilla();
		List<VersionPlantilla> versionesPlantillas = new ArrayList<VersionPlantilla>();
		Boolean hasElementos =false;
		if(registro.isPresent()) {
			registroDb  = registro.get();
			versionesPlantillas = registroDb.getPlantilla().getVersionesPlantilla();
			mav.addObject("versionesPlantillas", versionesPlantillas);
			mav.addObject("plantilla", registroDb.getPlantilla());
			mav.addObject("tipoRegistro", registroDb.getTipoRegistro());
			mav.addObject("registro", registroDb);
			mav.addObject("institucion", registroDb.getInstitucion());
			mav.addObject("success",success);

		}
		mav.addObject("urlGestion", "/plantillas/gestionar-plantillas/"+id + "/");		
		return mav;	
				
	}
	
	@PreAuthorize("hasUrl('/plantillas/gestionar-plantillas/habilitar/')")
	@PostMapping("/gestionar-plantillas/habilitar/")
	public ModelAndView cambiarPlantilla(@ModelAttribute(name="urlGestion") String urlGestion, 
										 @ModelAttribute(name="idPlantilla") Long idPlantilla,
										 @ModelAttribute(name="idVersionPlantilla") Long idVersionSel) {
		LOG.info("PUNTO 1");

		ModelAndView mav = new ModelAndView();
		String redireccion  = "redirect:" + urlGestion;
		if( idVersionSel > 0) {
			versionPlantillaServiceImpl.desabilitarTodasLasPlantillas(idPlantilla);
			versionPlantillaServiceImpl.habilitarVersionPlantilla(idVersionSel, idPlantilla);	
			LOG.info("PUNTO 4");
			System.out.println(redireccion);
			mav.addObject("success", "insert");		
		}
		mav.setViewName(redireccion);
		return mav;		
		
	}
	
	@PreAuthorize("hasUrl('/plantillas/columnasversionplantilla/{id}/')")
	@GetMapping("/columnasversionplantilla/{id}/")
	public ModelAndView ColumnasVP(@PathVariable("id") Long id) {
		ModelAndView mav  = new ModelAndView(GESTIONAR_COLUMNASVP);
		Optional<VersionPlantilla> vp = versionPlantillaServiceImpl.buscarVersionPlantilla(id);
			
		if(vp.isPresent()) {
			List<ColumnaVersionPlantilla> columnas = vp.get().getVersionesColumna();
			mav.addObject("columnas", columnas);
			mav.addObject("vp", vp.get());
			mav.addObject("tipo", vp.get().getPlantilla().getRegistro().get(0).getTipoRegistro().getId());
			mav.addObject("registro", vp.get().getPlantilla().getRegistro().get(0).getId());
		}
		return mav;	
				
	}
	
	
}
