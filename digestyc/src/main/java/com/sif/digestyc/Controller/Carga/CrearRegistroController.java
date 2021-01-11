package com.sif.digestyc.Controller.Carga;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Controller.utilidades.ManipularDate;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Entity.LoadModule.Institucion;
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Service.Load.CrearRegistroService;
import com.sif.digestyc.Service.Load.DirectorioService;
import com.sif.digestyc.Service.Load.InstitucionService;
import com.sif.digestyc.Service.Load.PeriodicidadService;
import com.sif.digestyc.Service.Load.PlantillaService;
import com.sif.digestyc.Service.Load.SectorService;
import com.sif.digestyc.Service.Load.TipoRegistroService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Controller
@RequestMapping("/registros")
public class CrearRegistroController {

	private static final Logger LOG=  LoggerFactory.getLogger(CrearRegistroController.class);
	private static final String FORM_CREAR_REGISTRO = "carga/registros/crearRegistro";
	@Autowired
	@Qualifier("crearRegistroService")
	private CrearRegistroService registroService;
	
	@Autowired
	@Qualifier("periodicidadService")
	private PeriodicidadService periodicidadService;
	
	@Autowired
	@Qualifier("tipoRegistroService")
	private TipoRegistroService tipoRegistroService;
	
	@Autowired
	@Qualifier("sectorService")
	private SectorService sectorService;
	
	@Autowired
	@Qualifier("plantillaService")
	private PlantillaService plantillaService;
	
	@Autowired
	@Qualifier("institucionServiceImpl")
	private InstitucionService institucionService;
	
	@Autowired
	@Qualifier("directorioServiceImpl")
	private DirectorioService directorioService;
	
	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaService;
	
	@PreAuthorize("hasUrl('/registros/crear')")
	@GetMapping("/crear")
	public ModelAndView formularioCrearRegistro(Model model,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success) { //pasamos el model a la URL Para poder por ejemplo llenarlos con los id 
		ModelAndView mav =  new ModelAndView(FORM_CREAR_REGISTRO);
		mav.addObject("error", error);
		mav.addObject("success", success);
		LOG.info("method: " + "formularioCrearRegistro()");
		Registro registro  = new Registro();
		
		
		mav.addObject("registroObject", registro);		
		mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
		mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
		mav.addObject("sectores", sectorService.listarSectores());
		mav.addObject("instituciones", institucionService.listarInstituciones());
		return mav;
	}
	
	@PreAuthorize("hasUrl('/registros/guardarregistro')")
	@PostMapping("/guardarregistro")
	public ModelAndView guardarRegistro(@Valid @ModelAttribute("registroObject") Registro registro,
										BindingResult result,
										@ModelAttribute("fechaInicioEntrega") String fechaInicio,
										@ModelAttribute("duracionPlazoEntrega") int diasPlazo,
										@ModelAttribute("institucion.id") Long id_institucion, 
										@RequestParam("archivo") MultipartFile archivoExcel) {
		ModelAndView mav=  new ModelAndView();
		
		
		String codigoGenerado = registroService.Fn_generarCodigoRegistro((long) registro.getTipoRegistro().getId());
		
		if (result.hasErrors() && !result.hasFieldErrors("activo")) {
			mav.setViewName(FORM_CREAR_REGISTRO);
			mav.addObject("error", "error");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
			mav.addObject("sectores", sectorService.listarSectores());
			mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
			System.out.println(result.getAllErrors());
			return mav;
		}
		try {
		Institucion institucion = institucionService.buscarInstitucionPorId(id_institucion);
		directorioService.crearDirectorio((institucion.getNombre()+"/"+registro.getNombre()).replace('/', '\\').replace(" ", "").trim());
		registro.setUbicacion((institucion.getNombre()+"/"+registro.getNombre()).replace('/', '\\').replace(" ", "").trim());
		registro.setFechaInicioEntrega(ManipularDate.formatearFecha(fechaInicio));
		registro.setFechaFinEntrega(ManipularDate.sumarDias(fechaInicio, diasPlazo));
		registro.setCodigo(codigoGenerado);
		registro.setEditable(true);

		//Asignar directorio activo
		Optional<Directorio> directorio = directorioService.obtenerDirectorioActivo(true);
		registro.setDirectorio(directorio.get());
		
		List<VersionPlantilla>  versionesPlantilla  =  new ArrayList<VersionPlantilla>();
		List<PlantillaColumna> columnasPre  = new ArrayList<>();
		
		List<String> columnas = plantillaService.leerCampos(archivoExcel);	
		
		
		Plantilla plantillaNueva =  new Plantilla();
		plantillaNueva.setNombre("PlantillaPrueba");
		plantillaNueva.setHabilitado(true);
		
		/*Crear una version de plantilla y setearla a plantilla*/
		VersionPlantilla versionPlantilla  = new VersionPlantilla();
		//versionPlantilla.setCodigo(codigoPlantilla);
		versionPlantilla.setPlantilla(plantillaNueva);
		versionPlantilla.setHabilitada(true);
		versionesPlantilla.add(versionPlantilla);
		plantillaNueva.setVersionesPlantilla(versionesPlantilla);
		
		/*Crear ColumnaVersionPlantilla*/
		List<ColumnaVersionPlantilla>  columnasVersionPlantilla = new ArrayList<ColumnaVersionPlantilla>();
		
				
		int orden=0;
		for(String col :  columnas) {
			PlantillaColumna  pCol  = new PlantillaColumna();			
			ColumnaVersionPlantilla colVersionPlantilla  = new ColumnaVersionPlantilla();
			
			pCol.setNombre(col.toUpperCase().trim().replace(" ", "_"));
			pCol.setCodigo(col.toUpperCase().trim().replace(" ", "_"));
			pCol.setOrden(orden);
			pCol.setActivo(true);
			pCol.setPlantilla(plantillaNueva);			
			orden++;
			
			/*Cada columna */
			colVersionPlantilla.setVersionPlantilla(versionPlantilla);
			colVersionPlantilla.setPlantillaColumna(pCol);
			columnasVersionPlantilla.add(colVersionPlantilla);
			
			columnasPre.add(pCol);
			
		}	
		plantillaNueva.setPlantillaColumnas(columnasPre);
		versionPlantilla.setVersionesColumna(columnasVersionPlantilla);			
		Optional<Plantilla> p = plantillaService.actualizar(plantillaNueva);
		
		String codigoPlantilla = versionPlantillaService.Fn_CodVersionPlantilla(((long) p.get().getId()));
		versionPlantilla.setCodigo(codigoPlantilla);
		versionPlantilla.setNombre(codigoPlantilla);
		registro.setPlantilla(p.get());
		registro.setActivo(true);
		registroService.guardarRegistro(registro);
		
		/**/

		
		mav.addObject("successCrear", true);
		}catch (Exception e) {
			mav.setViewName(FORM_CREAR_REGISTRO);
			mav.addObject("error", "error");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
			mav.addObject("sectores", sectorService.listarSectores());
			mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
			return mav;
		}		
		mav.setViewName("redirect:/registro/listar");
		return mav;
	}
}
	

