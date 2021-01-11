package com.sif.digestyc.Controller.Carga;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Repository.Carga.EntregaRepository;
import com.sif.digestyc.Service.Load.EntregaService;
import com.sif.digestyc.Service.Load.InstitucionService;
import com.sif.digestyc.Service.Load.PeriodicidadService;
import com.sif.digestyc.Service.Load.TipoRegistroService;

@Controller
@RequestMapping("/HistorialEntrega")
public class HistorialEntregaController {

	private static final String FORM_LISTAR_HENTREGA = "carga/Hentrega";

	@Autowired
	@Qualifier("EntregaServiceImpl")
	private EntregaService entregaService;

	@Autowired
	@Qualifier("entregaRepository")
	private EntregaRepository entregaRepository;

	@Autowired
	@Qualifier("institucionServiceImpl")
	private InstitucionService institucionService;

	@Autowired
	@Qualifier("tipoRegistroService")
	private TipoRegistroService tipoRegistroService;

	@Autowired
	@Qualifier("periodicidadService")
	private PeriodicidadService periodicidadService;

	// INDEX HISTORIAL ENTREGA
	@PreAuthorize("hasUrl('/HistorialEntrega/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoHentrega() {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_HENTREGA);
		mav.addObject("entregas", entregaService.findAll());
		mav.addObject("instituciones", institucionService.listarInstituciones());
		mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
		mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
		return mav;
	}

	@PreAuthorize("hasUrl('/HistorialEntrega/Filtro')")
	@GetMapping("/Filtro")
	public ModelAndView Filtro(@Param(value = "institucion") String institucion,
			@Param(value = "tiporegistro") String tiporegistro, @Param(value = "periodicidad") String periodicidad) {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_HENTREGA);
		mav.addObject("instituciones", institucionService.listarInstituciones());
		mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
		mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
		
		List<Entrega> filtro = entregaRepository.findByInstTipoPerio(institucion, tiporegistro, periodicidad);
		mav.addObject("entregas", filtro);
		
		/*List<Entrega> tipo = HentregaRepository.findByTipo(tiporegistro);
		mav.addObject("entregas", tipo);
		List<Entrega> inst = HentregaRepository.findByInstitucion(institucion); 
		mav.addObject("entregas", inst);
		List<Entrega> perio = HentregaRepository.findByPeriodicidad(periodicidad);
		mav.addObject("entregas", perio);*/		
		return mav;
	}
}
