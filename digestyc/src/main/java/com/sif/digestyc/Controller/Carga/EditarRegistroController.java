package com.sif.digestyc.Controller.Carga;

import java.util.ArrayList;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.sif.digestyc.Service.Load.DirectorioService;
import com.sif.digestyc.Service.Load.EditarRegistroService;
import com.sif.digestyc.Service.Load.EntregaService;
import com.sif.digestyc.Service.Load.InstitucionService;
import com.sif.digestyc.Service.Load.PeriodicidadService;
import com.sif.digestyc.Service.Load.PlantillaService;
import com.sif.digestyc.Service.Load.SectorService;
import com.sif.digestyc.Service.Load.TipoRegistroService;

@Controller
@RequestMapping("/registros")
public class EditarRegistroController {

	private static final String FORM_EDITAR_REGISTRO = "carga/registros/editarRegistro";
	@Autowired
	@Qualifier("editarRegistroService")
	private EditarRegistroService editarRegistroService;

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
	@Qualifier("EntregaServiceImpl")
	private EntregaService entregaService;
	private static final Logger LOG = LoggerFactory.getLogger(EditarRegistroController.class);

	@PreAuthorize("hasUrl('/registros/editarregistro')")
	@GetMapping("/editarregistro/{id}")
	public ModelAndView editarRegistro(@PathVariable("id") Long id,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success) {
		ModelAndView mav = new ModelAndView(FORM_EDITAR_REGISTRO);
		mav.addObject("error", error);
		mav.addObject("success", success);
		Registro registro = editarRegistroService.findById(id);// BuscarRegistro
		LOG.info("method: " + "EditarRegistro()");
		mav.addObject("registro", registro);
		mav.addObject("instituciones", institucionService.listarInstituciones());
		mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
		mav.addObject("sectores", sectorService.listarSectores());
		mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
		return mav;
	}

	@PreAuthorize("hasUrl('/registros/actualizarRegistro')")
	@PostMapping("/actualizarRegistro")
	public ModelAndView actualizarRegistro(@Valid @ModelAttribute("registro") Registro registro, BindingResult result,
			@ModelAttribute("institucion.id") Long id_institucion,
			@RequestParam(value = "archivo", required = false) MultipartFile archivoExcel) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors() && !result.hasFieldErrors("activo") && !result.hasFieldErrors("editable")) {
			mav.setViewName(FORM_EDITAR_REGISTRO);
			mav.addObject("error", "error");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
			mav.addObject("sectores", sectorService.listarSectores());
			mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
			System.out.println(result.getAllErrors());
			return mav;
		}

		Registro registroActualizado = editarRegistroService.findById(registro.getId());
		try {
			if (registroActualizado != null) {
				registroActualizado.setNombre(registro.getNombre());
				registroActualizado.setFechaInicioEntrega(registro.getFechaInicioEntrega());
				registroActualizado.setFechaFinEntrega(
						ManipularDate.sumarDias(registro.getFechaInicioEntrega(), registro.getDuracionPlazoEntrega()));
				registroActualizado.setDescripcion(registro.getDescripcion());
				registroActualizado.setPrioridad(registro.getPrioridad());
				registroActualizado.setDuracionPlazoEntrega(registro.getDuracionPlazoEntrega());

				// Ubicacion
				Institucion institucion = institucionService.buscarInstitucionPorId(id_institucion);
				LOG.info("ID institucion " + id_institucion);
				LOG.info("ID institucion " + institucion);
				// directorioService.crearDirectorio(institucion.getNombre()+"/"+registro.getNombre());
				//registroActualizado.setUbicacion((institucion.getNombre() + "/" + registro.getNombre()).replace('/', '\\'));

				// Asignar directorio activo
				Optional<Directorio> directorio = directorioService.obtenerDirectorioActivo(true);
				registroActualizado.setDirectorio(directorio.get());

				// SELECT
				registroActualizado.setInstitucion(registro.getInstitucion());
				registroActualizado.setPeriodicidad(registro.getPeriodicidad());
				//registroActualizado.setTipoRegistro(registro.getTipoRegistro());
				registroActualizado.setSector(registro.getSector());

				// EDITAR PLANTILLA
				if (archivoExcel != null && !archivoExcel.isEmpty()) {
					List<PlantillaColumna> columnasPre = new ArrayList<>();
					List<String> columnas = plantillaService.leerCampos(archivoExcel);
					Plantilla plantilla = registroActualizado.getPlantilla();
					int orden = 0;
					if (columnas.size() == plantilla.getPlantillaColumnas().size()) { // Tamaño igual de columnas
						for (PlantillaColumna col : plantilla.getPlantillaColumnas()) {
							col.setNombre(columnas.get(orden));
							columnasPre.add(col);
							orden++;
						}
						for (int i = orden; i < columnas.size(); i++) {
							PlantillaColumna pCol = new PlantillaColumna();
							pCol.setNombre(columnas.get(i));
							pCol.setOrden(orden);
							pCol.setActivo(false);
							pCol.setPlantilla(plantilla);
							columnasPre.add(pCol);
							orden++;
						}
						plantilla.setPlantillaColumnas(columnasPre);
						registroActualizado.setPlantilla(plantilla);
					} else if (columnas.size() <= plantilla.getPlantillaColumnas().size()
							|| columnas.size() >= plantilla.getPlantillaColumnas().size()) {// Más o menos Columnas

						List<VersionPlantilla> versionesPlantilla = new ArrayList<VersionPlantilla>();

						/* Crear una version de plantilla y setearla a plantilla */
						VersionPlantilla versionPlantilla = new VersionPlantilla();
						versionPlantilla.setCodigo("GENERADO NUEVA VERSIÓN");
						versionPlantilla.setPlantilla(plantilla);
						versionPlantilla.setHabilitada(true);
						versionesPlantilla.add(versionPlantilla);
						plantilla.setVersionesPlantilla(versionesPlantilla);
						LOG.info("plantilla : "  + plantilla.getId());
						LOG.info("Version plantilla : "  + versionPlantilla.getId());
						
						/* Crear ColumnaVersionPlantilla */
						List<ColumnaVersionPlantilla> columnasVersionPlantilla = new ArrayList<ColumnaVersionPlantilla>();
						//int or=0;
						for (String col : columnas) {
							PlantillaColumna pCol = new PlantillaColumna();
							ColumnaVersionPlantilla colVersionPlantilla = new ColumnaVersionPlantilla();

							pCol.setNombre(col);
							pCol.setOrden(orden);
							pCol.setActivo(true);
							pCol.setPlantilla(plantilla);
							orden++;
						
							/* Cada columna */
							colVersionPlantilla.setVersionPlantilla(versionPlantilla);
							colVersionPlantilla.setPlantillaColumna(pCol);
							columnasVersionPlantilla.add(colVersionPlantilla);

							columnasPre.add(pCol);
						}
						plantilla.setPlantillaColumnas(columnasPre);
						versionPlantilla.setVersionesColumna(columnasVersionPlantilla);
					}
				}
				editarRegistroService.updateRegistro(registroActualizado);
			}
			mav.addObject("successEdit", true);
			mav.setViewName("redirect:/registro/listar");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			mav.setViewName(FORM_EDITAR_REGISTRO);
			mav.addObject("error", "error");
			mav.addObject("instituciones", institucionService.listarInstituciones());
			mav.addObject("periodicidades", periodicidadService.listarPeriodicidades());
			mav.addObject("sectores", sectorService.listarSectores());
			mav.addObject("tipoRegistros", tipoRegistroService.listarTipoRegistros());
			return mav;
		}
		return mav;
	}

}
