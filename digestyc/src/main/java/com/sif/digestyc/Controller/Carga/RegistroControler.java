package com.sif.digestyc.Controller.Carga;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.opencsv.exceptions.CsvValidationException;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Entity.LoadModule.Periodo;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Service.Load.ArchivoDbfService;
import com.sif.digestyc.Service.Load.ArchivoExcelService;
import com.sif.digestyc.Service.Load.ArchivoService;
import com.sif.digestyc.Service.Load.ArchivoSpssService;
import com.sif.digestyc.Service.Load.ArchivoTxtService;
import com.sif.digestyc.Service.Load.CargarArchivoService;
import com.sif.digestyc.Service.Load.EntregaService;
import com.sif.digestyc.Service.Load.FormatoService;
import com.sif.digestyc.Service.Load.HistorialEntregaService;
import com.sif.digestyc.Service.Load.InsertarEnTablaService;
import com.sif.digestyc.Service.Load.RegistroService;
import com.sif.digestyc.Service.Load.TablaService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;
import com.sif.digestyc.Service.Load.CargaImpl.PeriodoServiceImpl;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

@Controller
@RequestMapping("/registro")

public class RegistroControler {
	private static final Logger LOG = LoggerFactory.getLogger(RegistroControler.class);

	private static final String FORM_LISTAR_REGISTRO = "carga/listadoRegistro";
	private static final String FORM_LISTAR_ESTADOREGISTRO = "carga/listadoEstadoRegistro";
	// private static final String CARGAR = "/registro/ListarEstado?id=";
	public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

	@Autowired
	@Qualifier("registroServiceImpl")
	private RegistroService registroService;

	@Autowired
	@Qualifier("archivoExcelService")
	private ArchivoExcelService archivoExcel;

	@Autowired
	@Qualifier("EntregaServiceImpl")
	private EntregaService entregaService;

	@Autowired
	@Qualifier("HentregaServiceImpl")
	private HistorialEntregaService historialentregaService;

	@Autowired
	@Qualifier("tablaServiceImpl")
	private TablaService tablaServiceImpl;

	@Autowired
	@Qualifier("ArchivoServiceImpl")
	private ArchivoService archivoServiceImpl;

	@Autowired
	@Qualifier("cargarArchivoServiceImpl")
	private CargarArchivoService cargarArchivoService;

	@Autowired
	@Qualifier("insertarEnTablaServiceImpl")
	private InsertarEnTablaService insertarEnTablaServiceImpl;

	@Autowired
	private CargaDbfController cargaDbfComponent;

	@Autowired
	@Qualifier("archivoDbfServiceImpl")
	private ArchivoDbfService archivoDbfService;

	@Autowired
	@Qualifier("archivoExcelService")
	private ArchivoExcelService archivoExcelService;

	@Autowired
	private CargaExcelController cargaExcelComponent;

	@Autowired
	@Qualifier("formatoServiceImpl")
	private FormatoService formatoServiceImpl;

	@Autowired
	private CargaSpssController cargaSpssComponent;

	@Autowired
	@Qualifier("archivoSpssServiceImpl")
	private ArchivoSpssService archivoSpssService;

	@Autowired
	@Qualifier("archivoTxtServiceImpl")
	private ArchivoTxtService archivoTxtService;
	

		
	/*
	 * @Autowired
	 * 
	 * @Qualifier("ArchivoTxtServiceImpl") private ArchivoTxtService
	 * archivoTxtService;
	 */
	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;

	@Autowired
	private CargaTxtController cargaTxtComponent;
	
	
	/* QUITAR LUEGO PORFA*/
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private PeriodoServiceImpl periodoService;
	
	// Es solo un metodo para nosotros, no validare nada pero dejare un try catch para que en la mayoria de errores deje un mensaje en consola
	// Causas que podrian dar excepcion: 
	//   Si no estas logeado
	//   Registro no existe
	//   no se puede crear periodo
	
	@PostMapping("/crear_entrega_de_prueba_que_alguien_quitara_luego")
	public String insertEntrega(@RequestParam(name = "registro_id", required = true) Long registro_id) {
		LOG.warn("CREANDO ENTREGA DE PRUEBA"+registro_id);

		try {
			Registro registro = registroService.buscarRegistroPorId(registro_id);
			Periodo periodo = new Periodo(null, "PERIDOO PRUEBA R No.: "+registro.getId(), new Date(), new Date(), registro.getId().intValue(), registro.getPeriodicidad(), new ArrayList<Entrega>());
			periodo = periodoService.update(periodo);
			SecurityContext context = SecurityContextHolder.getContext(); 
			Authentication authentication = context.getAuthentication(); 
			org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
			Usuario usuario = usuarioService.findByUsername(user.getUsername());

			Entrega entrega = new Entrega();
			entrega.setFechaInicioEntrega(registro.getFechaInicioEntrega());
			entrega.setFechaFinEntrega(registro.getFechaFinEntrega());
			entrega.setRegistro(registro);
			entrega.setUsuario(usuario);
			entrega.setVersionPlantilla(versionPlantillaServiceImpl.recuperarVersionPlantillaHabilitada(registro_id));
			entrega.setPeriodo(periodo);
			entregaService.actualizar(entrega);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/registro/listar";
	}
	

	// INDEX REGISTRO
	@PreAuthorize("hasUrl('/registro/listar')")
	@GetMapping("/listar")
	public ModelAndView listadoRegistro(Model model,
			@RequestParam(name = "successCrear", required = false) String successCrear,
			@RequestParam(name = "successEdit", required = false) String successEdit) {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_REGISTRO);
		mav.addObject("registros", registroService.findAll());
		mav.addObject("successCrear", successCrear);
		mav.addObject("successEdit", successEdit);
		Registro registro = new Registro();
		model.addAttribute("registroObject", registro);
		return mav;
	}

	@PreAuthorize("hasUrl('/registro/ListarEstado')")
	@GetMapping("/ListarEstado")
	public ModelAndView listadoEstado(Model model, @RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "id_estado_anio", required = false) Integer id_estado_anio,
			// @RequestParam(name = "id_registro", required=true) Long id_registro,
			@RequestParam(name = "anio", required = false) Integer anio,
			@RequestParam(name = "success", required = false) String success,
			@RequestParam(name = "errorColumnas", required = false) String errorColumnas) {
		LOG.info("method : listarEstado()");
		ModelAndView mav = new ModelAndView(FORM_LISTAR_ESTADOREGISTRO);
		model.addAttribute("standardDate", new Date());
		if (id_estado_anio != null) {
			if (id_estado_anio == 1) {
				mav.addObject("entregas", entregaService.buscarEntregaRegistroPorAnio((long) id, (long) anio));
			}
		} else {
			mav.addObject("entregas", entregaService.buscarEntregaRegistroTodos((long) id));
		}
		mav.addObject("registro", registroService.buscarRegistroPorId((long) id));
		mav.addObject("etapas", historialentregaService.estado((long) id));
		mav.addObject("anios", entregaService.añosEntregas((long) id));
		mav.addObject("anio", entregaService.EntregaRegistroPorAnio());
		mav.addObject("success", success);
		mav.addObject("errorColumnas", errorColumnas);
		return mav;
	}

	@PreAuthorize("hasUrl('/registro/Filtro')")
	@GetMapping("/Filtro")
	public ModelAndView Filtro(@RequestParam(name = "anios", required = false) int anio,
			@RequestParam(name = "id_registro", required = true) Integer id_registro) {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_ESTADOREGISTRO);

		if (anio == 1) {
			mav.addObject("entregas", entregaService.buscarEntregaRegistroTodos((long) id_registro));
		} else {
			mav.addObject("entregas", entregaService.buscarEntregaRegistroPorAnio((long) id_registro, (long) anio));
		}
		mav.addObject("registro", registroService.buscarRegistroPorId((long) id_registro));
		mav.addObject("etapas", historialentregaService.estado((long) id_registro));
		mav.addObject("anios", entregaService.añosEntregas((long) id_registro));
		mav.addObject("anio", anio);
		return mav;
	}

	@PreAuthorize("hasUrl('/registro/upload')")
	@PostMapping("/upload")
	public ModelAndView uploadFile(Model model, @RequestParam("file") MultipartFile file,
			@RequestParam(name = "id_periodo", required = true) Long id_periodo,
			@ModelAttribute(name = "id_entrega") Long id_entrega,
			@RequestParam(name = "id_registro", required = true) Long id_registro,
			@RequestParam(name = "anio", required = false) int anio) throws IOException, CsvValidationException {
		ModelAndView mav = new ModelAndView(FORM_LISTAR_ESTADOREGISTRO);

		Map<String, Object> resultados = new HashMap<>();
		Entrega entrega = entregaService.buscarEntrega(id_entrega);
		resultados = entregaService.realizarEntregaArchivo(file, id_periodo, id_entrega, id_registro, anio, false);
		Boolean iguales = true;
		String directorioN = null;
		Entrega entregaSubida = new Entrega();
		if (resultados.containsKey("directorioN")) {
			directorioN = resultados.get("directorioN").toString();
		}
		if (resultados.containsKey("iguales")) {
			iguales = Boolean.valueOf(resultados.get("iguales").toString());
		}
		if (resultados.containsKey("entregaVersionPlantilla")) {
			entregaSubida = (Entrega) resultados.get("entregaVersionPlantilla");
		}

		if (iguales == false) {
			File archivo1 = new File(directorioN); // se elimina archivo
			 try{
		    	 if(archivo1.delete()){
		    	    System.out.println(archivo1.getName() + " is deleted!");
		         }else{
		    	    System.out.println("Delete failed: File didn't delete");
		    	  }
		       }catch(Exception e){
		           System.out.println("Exception occurred");
		    	   e.printStackTrace(System.out);
		    	}
			mav.addObject("errorColumnas", "columnas");
		} else {
			historialentregaService.actualizarEstado((long) id_registro, (long) id_periodo,
					(String) "Se carga archivo");
			entregaService.asignarVersionPlantillaUsadaParaSubirArchivo(entregaSubida);
			mav.addObject("success", "anexar");
		}

		mav.addObject("anio", anio);
		mav.setViewName("redirect:/registro/ListarEstado?id=" + id_registro);
		return mav;

	}

	@PreAuthorize("hasUrl('/registro/sustituir-entrega')")
	@PostMapping("/sustituir-entrega")
	public ModelAndView uploadFile4(Model model, @RequestParam("file") MultipartFile file,
			@RequestParam(name = "id_periodo_3", required = true) Long idPeriodo,
			@RequestParam(name = "id_registro_3", required = true) Long idRegistro,
			@RequestParam(name = "id_entrega_3", required = true) Long idEntrega,
			@RequestParam(name = "anio", required = false) int anio) {
		ModelAndView mav = new ModelAndView();
		Entrega entrega = entregaService.buscarEntrega(idEntrega);
		entregaService.setFalsoEstadoEntrega(idEntrega);/* Ya que se borran los datos de la BD y el archivo se cambia */

		entregaService.borrarDatosEntrega(idRegistro, idEntrega);
		Boolean iguales = true;
		String directorioN = null;
		Entrega entregaSubida = new Entrega();
		File archivoAntiguo = new File(entrega.getUbicacion());
		if (archivoAntiguo != null) {
			archivoAntiguo.delete();
		}
		Map<String, Object> resultados = new HashMap<>();
		resultados = entregaService.realizarEntregaArchivo(file, idPeriodo, idEntrega, idRegistro, anio, true);
		if (resultados.containsKey("directorioN")) {
			directorioN = resultados.get("directorioN").toString();
		}
		if (resultados.containsKey("iguales")) {
			iguales = Boolean.valueOf(resultados.get("iguales").toString());
		}
		if (resultados.containsKey("entregaVersionPlantilla")) {
			entregaSubida = (Entrega) resultados.get("entregaVersionPlantilla");
		}

		if (iguales == false) {
			File archivo1 = new File(directorioN); // se elimina archivo
			 try{
		    	 if(archivo1.delete()){
		    	    System.out.println(archivo1.getName() + " is deleted!");
		         }else{
		    	    System.out.println("Delete failed: File didn't delete");
		    	  }
		       }catch(Exception e){
		           System.out.println("Exception occurred");
		    	   e.printStackTrace(System.out);
		    	}
			mav.addObject("errorColumnas", "columnas");
			
		} else {
			historialentregaService.actualizarEstado((long) idRegistro, (long) idPeriodo, (String) "Se carga archivo");
			entregaService.asignarVersionPlantillaUsadaParaSubirArchivo(entregaSubida);
			mav.addObject("success", "campo");
			mav.addObject("success", "cambio");
		}
		
		mav.addObject("anio", anio);
		mav.setViewName("redirect:/registro/ListarEstado?id=" + idRegistro);
		return mav;
	}

	@PreAuthorize("hasUrl('/registro/upload/carga-datos/{id}/{idRegistro}/{idPeriodo}/')")
	@GetMapping("/upload/carga-datos/{id}/{idRegistro}/{idPeriodo}/")
	public ModelAndView cargarDatosDeEntrega(@PathVariable(name = "id") Long id,
			@PathVariable(name = "idRegistro") Long idRegistro, @PathVariable(name = "idPeriodo") Long idPeriodo) {
		ModelAndView mav = new ModelAndView();
		String codigo = entregaService.Fn_EntregaArchivoNombre((long) idRegistro, (long) idPeriodo);
		Entrega entrega = entregaService.buscarEntrega(id);
		String directorioN = entrega.getUbicacion();
		
		String[] partes = directorioN.split("\\.");
		Formato formato = new Formato();
		int f = 0;

		formato = formatoServiceImpl.buscarFormatoPorExtension("." + partes[1].toLowerCase());
		if (formato == null) {

		}
		switch (partes[1].toLowerCase()) {// SE IDENTIFICA FORMATO DEL ARCHIVO CARGADO

		case "xls":
			f = 1;
			break;
		case "xlsx":
			f = 1;
			break;
		case "txt":
			f = 2;
			break;
		case "csv":
			f = 3;
			break;
		case "mdb":
			f = 4;
			break;
		case "sav":
			f = 5;
			break;
		case "dbf":
			f = 6;
			break;
		default:
			f = 0;

		}
		if (entrega.getArchivo() != null) {
			Long idArchivo = entrega.getArchivo().getId();
			archivoServiceImpl.cambiarEstadoArchivoCargado(idArchivo); // actualizar a cargado y la fecha de subida
		} else if (entrega.getArchivo() == null) {
			entregaService.nuevaEntrega((long) idRegistro, entrega.getId(), (long) f, codigo);
		}
		String tnombre = registroService.codigoRegistro((long) idRegistro); // SE RECUPERA CODIGO DE REGISTRO
		switch (f) {
		case 1:// xlsx
			cargaExcelComponent.crearMetadataDeTablaExcel((long) idRegistro, entrega.getId(), tnombre, formato,
					directorioN);
			mav.addObject("success", "insert");
			break;
		case 2:// txt
			cargaTxtComponent.crearMetadataDeTablaTxt((long) idRegistro, entrega.getId(), tnombre, formato, directorioN,
					f, codigo);
			mav.addObject("success", "insert");
			break;
		case 3:// csv
			cargaTxtComponent.crearMetadataDeTablaTxt((long) idRegistro, entrega.getId(), tnombre, formato, directorioN,
					f, codigo);
			mav.addObject("success", "insert");
			break;
		case 4:// mdb
			break;
		case 5:// sav
			cargaSpssComponent.crearMetadataDeTablaSpss((long) idRegistro, entrega.getId(), tnombre, formato,
					directorioN);
			mav.addObject("success", "insert");
			break;
		case 6:// dbf
			cargaDbfComponent.crearMetadataDeTablaDbf((long) idRegistro, entrega.getId(), tnombre, formato,
					directorioN);
			mav.addObject("success", "insert");
			break;
		}

		mav.setViewName("redirect:/registro/ListarEstado" + "?id=" + idRegistro);
		return mav;
	}

	@PreAuthorize("hasUrl('/registro/upload/eliminar-datos/{id}/{idRegistro}/{idPeriodo}/')")
	@GetMapping("/upload/eliminar-datos/{id}/{idRegistro}/{idPeriodo}/")
	public ModelAndView eliminarDatosDeEntrega(@PathVariable(name = "id") Long id,
			@PathVariable(name = "idRegistro") Long idRegistro, @PathVariable(name = "idPeriodo") Long idPeriodo) {
		ModelAndView mav = new ModelAndView();
		Entrega entrega = entregaService.buscarEntrega(id);
		entregaService.borrarDatosEntrega(idRegistro, id);
		entregaService.setFalsoEstadoEntrega(id);

		String directorioN = entrega.getUbicacion();
		mav.addObject("success", "delete");
		mav.setViewName("redirect:/registro/ListarEstado" + "?id=" + idRegistro);
		return mav;
	}

	@GetMapping("/Descargar")
	public void descargar(HttpServletResponse response,
			@RequestParam(name = "registro_id", required = false) int registro_id,
			@RequestParam(name = "periodo_id", required = false) int periodo_id) {
		String directorio = entregaService.directorio() + "DIGESTYC_R1_P2";
		response.setContentType(directorio);
	}

}
