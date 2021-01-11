package com.sif.digestyc.Controller.Carga;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.Plantilla;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.Tipificacion;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Service.Load.CargarArchivoService;
import com.sif.digestyc.Service.Load.ColumnaVersionPlantillaService;
import com.sif.digestyc.Service.Load.PlantillaService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;
import com.sif.digestyc.Service.Load.CargaImpl.CargarArchivoServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.CrearRegistroServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.PlantillaColumnaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TipificacionServiceImpl;

@Controller
@RequestMapping("/identificacion")
public class IdentificacionController {
	private static final Logger LOG = LoggerFactory.getLogger(IdentificacionController.class);
	private static final String COLUMNAS = "/carga/identificacion/identificacion";
	private static final String NUEVAS_COLUMNAS = "/carga/identificacion/nuevaPlantilla";

	@Autowired
	@Qualifier("crearRegistroService")
	private CrearRegistroServiceImpl registroService;

	@Autowired
	private TipificacionServiceImpl tipificacionService;

	@Autowired
	@Qualifier("plantillaColumnaServiceImpl")
	private PlantillaColumnaServiceImpl plantillaColumnaService;

	@Autowired
	@Qualifier("plantillaService")
	private PlantillaService plantillaService;

	@Autowired
	@Qualifier("columnaVersionPlantillaServiceImpl")
	private ColumnaVersionPlantillaService columnaVersionPlantillaService;

	@Autowired
	private CargarArchivoServiceImpl cargaService;

	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;
	
	
	@Autowired
	@Qualifier("cargarArchivoServiceImpl")
	private CargarArchivoService cargarArchivoServiceImpl;
	
	
	//PARA IDENTIFICACIÓN DE COLUMNAS (ASIGNAR TIPIFICACIÓN)
	
	/* METODO REFACTORIZADO CORRECTAMENTE */
	@PreAuthorize("hasUrl('/identificacion/columnas')")
	@GetMapping("/columnas/{id}")
	public ModelAndView columnas(@PathVariable("id") Long id,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "numEnt", required = false) Integer numEnt,
			@RequestParam(name = "unico", required = false) String unico) {
		Optional<Registro> registro = registroService.buscarPorId(id);
		ModelAndView mav = new ModelAndView(COLUMNAS);

		LOG.info("Registro ID " + registro.get().getId());
		VersionPlantilla versionPlantillaHabilitada = versionPlantillaServiceImpl
				.recuperarVersionPlantillaHabilitada(registro.get().getId());
		if (registro.isPresent() && versionPlantillaHabilitada !=null) {
			mav.addObject("tipificaciones",
					tipificacionService.buscarPorInstitucion(registro.get().getInstitucion().getId().intValue()));
			mav.addObject("registro", registro.get());
			mav.addObject("tipoRegistro", registro.get().getTipoRegistro());

			/* Aqui va faltar recuperar solo la plantilla que esta habilitada */
			List<ColumnaVersionPlantilla> columnas = versionPlantillaHabilitada.getVersionesColumna();
			/**/
			mav.addObject("error", error);
			mav.addObject("success", success);
			mav.addObject("numEnt", numEnt); //Ya no
			mav.addObject("unico", unico); //Ya no
			mav.addObject("columnas", columnas);
			mav.addObject("nuevaVersionColumna", new ColumnaVersionPlantilla());
			//puede editar?
			mav.addObject("editar", versionPlantillaServiceImpl.puedeEditar(versionPlantillaHabilitada.getId()));
		}
		return mav;
	}

	/* METODO REFACTORIZADO CORRECTAMENTE */
	@PreAuthorize("hasUrl('/identificacion/guardar')")
	@PostMapping("/guardar")
	public String guardar(Model model, @ModelAttribute("id") Long id,
			@ModelAttribute("id_institucion") Long id_institucion, HttpServletRequest request) {
		LOG.info("Estas en el metodo post");
		String redireccion = "redirect:/identificacion/columnas/" + id + "/";
		Optional<Registro> registro = registroService.buscarPorId(id);

		if (request.getParameterValues("tipificacion") != null && registro.isPresent()
				&& registro.get().getEditable()) {
			for (String t : request.getParameterValues("tipificacion")) {
				if (t.matches("[0-9]*,[0-9]*")) {
					try {
						int cId = Integer.parseInt(t.split(",")[0].trim());
						int tId = Integer.parseInt(t.split(",")[1].trim());
						Optional<ColumnaVersionPlantilla> pVerCol = columnaVersionPlantillaService
								.buscarVersionColumna((long) cId);
						// ColumnaVersionPlantilla pColumna = plantillaColumnaService.buscarPorId(cId)

						Tipificacion tipificacion = tipificacionService.buscarPorId(tId);
						if (pVerCol.get() != null && tipificacion != null) {
							pVerCol.get().setTipificacion(tipificacion);
							// pColumna.setTipificacion(tipificacion);
							columnaVersionPlantillaService.actualizar(pVerCol.get());
							// plantillaColumnaService.actualizar(pColumna);
						}						
					}catch(Exception e){
					  e.printStackTrace(System.out);
					}
				}

			}
			redireccion += "?success=edit";
		} else {
			redireccion += "?error=edit";
		}
		return redireccion;
	}
	
	/* ESTE YA GENERA LA PLANTILLA CORRECTAMENTE */
	@PreAuthorize("hasUrl('/identificacion/importeMasivo')")
	@GetMapping("/importeMasivo/{id}/")
	public ResponseEntity<InputStreamResource> importeMasivo(@PathVariable("id") Long id) throws IOException {
		Optional<Registro> registro = registroService.buscarPorId(id);
		ByteArrayInputStream stream = plantillaColumnaService.paraImporteMasivo(registro);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=importeMasivo.xlsx");
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
	}
	
	
	

	//PARA CREAR VERSIÓN DE PLANTILLAS
	
	//INDEX NUEVA PLANTILLA
	@PreAuthorize("hasUrl('/identificacion/nuevaPlantilla/{id}')")
	@SuppressWarnings("unchecked")
	@GetMapping("/nuevaPlantilla/{id}")
	public ModelAndView nuevaPlantilla(@PathVariable("id") Long id,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "unico", required = false) String unico, HttpSession session,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView(NUEVAS_COLUMNAS);
		Optional<Registro> registro = registroService.buscarPorId(id);

		List<String> columnas = (List<String>) request.getSession().getAttribute("COLUMNAS_SESSION");
		if (columnas != null) {
			columnas = (List<String>) session.getAttribute("COLUMNAS_SESSION");
		} else {
			if (registro.isPresent()) {
				List<PlantillaColumna> columnasTemporales = registro.get().getInstitucion() != null
						? plantillaColumnaService.getColumnasDePlantilla(registro.get().getPlantilla().getId())
						: new ArrayList<>();
				mav.addObject("registro", registro.get());
				mav.addObject("tipoRegistro", registro.get().getTipoRegistro());
				mav.addObject("error", error);
				mav.addObject("success", success);
				mav.addObject("unico", unico);
				columnas = new ArrayList<>();
				for (PlantillaColumna pc : columnasTemporales) {
					columnas.add(pc.getCodigo());
				}
				mav.addObject("COLUMNAS_SESSION", columnas);
				mav.addObject("nuevaVersionColumna", new ColumnaVersionPlantilla());
			}
		}
		return mav;
	}

	@PreAuthorize("hasUrl('/identificacion/guardar/nuevaPlantilla')")
	@PostMapping("/guardar/nuevaPlantilla") // id es el id del registro
	public ModelAndView guardarPlantilla(Model model, @ModelAttribute("id") Long id,
			@ModelAttribute("id_institucion") Long id_institucion, HttpServletRequest request,
			@ModelAttribute("redireccion") String redireccionValor) {
		ModelAndView mav = new ModelAndView();
		Optional<Registro> registro = registroService.buscarPorId(id);
		List<String> columnas = new ArrayList<String>();
		Boolean algunaVersionIgual = false;
		if (registro.isPresent()) {
			if (request.getParameterValues("columna_plantilla_nueva") != null) {
				for (String col : request.getParameterValues("columna_plantilla_nueva")) {
					columnas.add(col.trim());
				}
				if (registro.isPresent()) {
					for (VersionPlantilla vp : registro.get().getPlantilla().getVersionesPlantilla()) {
						List<String> columnasDb  =  new ArrayList<String>();
						for(ColumnaVersionPlantilla colverpla : vp.getVersionesColumna()) {
							columnasDb.add(colverpla.getPlantillaColumna()!=null?colverpla.getPlantillaColumna().getCodigo():null);
						}
						if (algunaVersionIgual == false) {
							Boolean plantillaActual = true;// significa que es igual la plantilla
							Integer cantidadColNuevaPlantilla =  columnas.size();
							Integer verificarCantidad = 0;
							Integer numCamposDb = columnasDb.size();
							for (String colPorGuardar: columnas) {
								if (!columnasDb.contains(colPorGuardar)) {
									plantillaActual = false;
									break;// se sale de este for, la plantilla actual es diferente
								}
								verificarCantidad++;
							}
							if (plantillaActual == true 
									&& (verificarCantidad == cantidadColNuevaPlantilla)
									&& (verificarCantidad == numCamposDb)) { // si se mantuvo en true, la version de plantilla es igual a la que se desea crear
								algunaVersionIgual = true;
								break;
							}else if(plantillaActual == true 
									&& (verificarCantidad == cantidadColNuevaPlantilla)
									&& (verificarCantidad < numCamposDb)){
								 algunaVersionIgual = false;
								
							}
						} else {
							break;
						}

					}
					if (algunaVersionIgual == false) {
						/*Parámetros: (idRegistro, columnasNuevas)*/
						versionPlantillaServiceImpl.crearNuevaVersionPlantilla(id, columnas);
						if (registro.get().getTipoRegistro().getId() == 2) {
							redireccionValor = "/plantillas/oe/" + registro.get().getInstitucion().getId() + "/?success=insert";
						} else {
						   redireccionValor = "/plantillas/ra/" + registro.get().getInstitucion().getId() + "/?success=insert";
						}
					}else {
						//Si algunaVersionIgual existe debería usar esa
						redireccionValor = "/identificacion/nuevaPlantilla/" + id + "/?error=repetido";
					}
				}
				cargarArchivoServiceImpl.actualizarColumnas(columnas, registro.get().getCodigo());
			}
			mav.setViewName("redirect:" + redireccionValor);
		}
		return mav;
	}

	/* METODO REFACTORIZADO CORRECTAMENTE, "SOLO PARA EXCEL" */
	// @PreAuthorize("hasUrl('/identificacion/importarNueva')")
	@PostMapping("/importarNueva")
	public ModelAndView importarNueva(@RequestParam("archivo") MultipartFile archivoExcel,
			@ModelAttribute("plantilla") Long id, @ModelAttribute("registro") Long registro,
			@ModelAttribute("redireccion") String redireccionValor) throws IOException {
		ModelAndView mav = new ModelAndView();
		Optional<Registro> reg = registroService.buscarPorId(registro);
		if (archivoExcel != null && !archivoExcel.isEmpty() && reg.get().getInstitucion() != null) {
			Map<String, String> columnas = plantillaService.leerImporteConCabeceras(archivoExcel);
			VersionPlantilla versionPlantillaHabilitada = versionPlantillaServiceImpl
					.recuperarVersionPlantillaHabilitada(reg.get().getId());
			VersionPlantilla nueva = new VersionPlantilla();
			nueva.setCodigo(versionPlantillaHabilitada.getCodigo());
			nueva.setHabilitada(true);
			nueva.setPlantilla(versionPlantillaHabilitada.getPlantilla());
			nueva.setNombre(versionPlantillaHabilitada.getNombre());
			versionPlantillaHabilitada.setHabilitada(false);
			versionPlantillaServiceImpl.actualizar(versionPlantillaHabilitada);
			versionPlantillaHabilitada = versionPlantillaServiceImpl.actualizar(nueva);

			List<PlantillaColumna> columnasValidas = plantillaColumnaService
					.PlanillaColumnasPorInstitucion(reg.get().getInstitucion().getId());
			Iterator<String> iterador = columnas.keySet().iterator();
			List<Tipificacion> tipificaciones = tipificacionService
					.buscarPorInstitucion(reg.get().getInstitucion().getId().intValue());
			List<ColumnaVersionPlantilla> pColumnas = new ArrayList<ColumnaVersionPlantilla>();
			while (iterador.hasNext()) {
				String key = iterador.next(); // Columna
				String value = columnas.get(key); // valor de esa columna
				Optional<Tipificacion> tipificacion = tipificaciones.stream()
						.filter(t -> t.getNombre().equalsIgnoreCase(value)).findFirst();
				Optional<PlantillaColumna> pColumna = columnasValidas.stream()
						.filter(p -> p.getNombre().equalsIgnoreCase(key)).findFirst();
				if (tipificacion.isPresent() && pColumna.isPresent()) {
					ColumnaVersionPlantilla pc = new ColumnaVersionPlantilla();
					pc.setTipificacion(tipificacion.get());
					pc.setPlantillaColumna(pColumna.get());
					pc.setVersionPlantilla(versionPlantillaHabilitada);
					pColumnas.add(pc);
				}
			}
			versionPlantillaHabilitada.setVersionesColumna(pColumnas);
			versionPlantillaServiceImpl.actualizar(versionPlantillaHabilitada);
		}
		if (redireccionValor != null && redireccionValor.contains("/identificacion/nuevaPlantilla/")) {
			actualizarTabla(reg.get());

			mav.setViewName("redirect:" + redireccionValor + "?success=edit");
		} else {
			mav.setViewName("redirect:/identificacion/columnas/" + registro);
		}
		return mav;
	}
	

	private void actualizarTabla(Registro registro) {
		if (registro != null) {
			VersionPlantilla nuevaVersion = versionPlantillaServiceImpl
					.recuperarVersionPlantillaHabilitada(registro.getId());
			List<ColumnaVersionPlantilla> versionActual = nuevaVersion.getVersionesColumna();
			List<String> columnas = new ArrayList<>();
			for (ColumnaVersionPlantilla col : versionActual) {
				columnas.add(col.getPlantillaColumna().getCodigo());
			}
			cargaService.actualizarColumnas(columnas, registro.getCodigo());
		}
	}
	
	@PreAuthorize("hasUrl('/identificacion/nueva-plantilla/{columna}/eliminar/')")
	@SuppressWarnings("unchecked")
	@GetMapping("/nueva-plantilla/{columna}/eliminar/")
	public ModelAndView eliminarColumnaNuevaPlantilla(@PathVariable("columna") String columna, HttpSession session,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		List<String> columnas = (List<String>) request.getSession().getAttribute("COLUMNAS_SESSION");
		if (columnas != null) {
			columnas.contains(columna);
			columnas.remove(columna);
		}

		return mav;
	}
	
	
}
