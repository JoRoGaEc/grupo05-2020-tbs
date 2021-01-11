package com.sif.digestyc.Service.Load.CargaImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sif.digestyc.Controller.Carga.CargaDbfController;
import com.sif.digestyc.Controller.Carga.CargaExcelController;
import com.sif.digestyc.Controller.Carga.CargaSpssController;
import com.sif.digestyc.Controller.Carga.CargaTxtController;
import com.sif.digestyc.Controller.Carga.RegistroControler;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.VersionPlantilla;
import com.sif.digestyc.Repository.Carga.EntregaRepository;
import com.sif.digestyc.Repository.Carga.RegistroRepository;
import com.sif.digestyc.Repository.Carga.TablaRepository;
import com.sif.digestyc.Service.Especial.FicherosService;
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
import com.sif.digestyc.Service.Load.TablaService;
import com.sif.digestyc.Service.Load.VersionPlantillaService;

@Service("EntregaServiceImpl")
public class EntregaServiceImpl implements EntregaService {

	private static final Logger LOG = LoggerFactory.getLogger(EntregaServiceImpl.class);

	@Autowired
	@Qualifier("entregaRepository")
	private EntregaRepository entregaRepository;
	
	@Autowired
	@Qualifier("archivoExcelService")
	private ArchivoExcelService archivoExcel;
	
	@Autowired
	private TablaRepository tablaRepository;

	@Autowired
	@Qualifier("registroRepository")
	private RegistroRepository registroRepositoryImpl;
	
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

	@Autowired
	@Qualifier("versionPlantillaServiceImpl")
	private VersionPlantillaService versionPlantillaServiceImpl;

	@Autowired
	private CargaTxtController cargaTxtComponent;
	
	
	@Autowired
	@Qualifier("ficherosServiceImpl")
	private FicherosService ficherosServices;

	@Override
	@Transactional
	public List<Entrega> findAll() {
		return (List<Entrega>) entregaRepository.findAll();
	}

	@Override
	@Transactional
	public List<Entrega> buscarEntregaRegistroPorId(Long id) {
		return (List<Entrega>) entregaRepository.buscarEntregaRegistroPorId(id);

	}

	@Override
	@Transactional
	public void nuevaEntrega(Long registro_id, Long entrega_id, Long formato, String codigo) {

		entregaRepository.nuevaEntrega(registro_id, entrega_id, formato, codigo);

		System.out.println("Entro a nueva entrega" + registro_id + "," + entrega_id + "," + formato + "," + codigo);
		// return archivo;
	}

	@Override
	@Transactional
	public void colocarVersionPlantilla(Long entrega_id, Long version_plantilla_id) {
		entregaRepository.colocarVersionPlantilla(entrega_id, version_plantilla_id);
	}

	@Override
	@Transactional
	public int Fn_VerificarEntregaArchivo(Long registro_id, Long periodo_id) {
		return entregaRepository.Fn_VerificarEntregaArchivo(registro_id, periodo_id);
	}

	@Override
	@Transactional
	public String Fn_EntregaArchivoNombre(Long registro_id, Long periodo_id) {
		return entregaRepository.Fn_EntregaArchivoNombre(registro_id, periodo_id);
	}

	@Override
	@Transactional
	public String directorio() {
		return entregaRepository.directorio();
	}

	@Override
	@Transactional
	public List<String> añosEntregas(Long id) {
		return entregaRepository.añosEntregas(id);
	}

	@Override
	@Transactional
	public List<Entrega> buscarEntregaRegistroPorAnio(Long id, Long anio) {
		return entregaRepository.buscarEntregaRegistroPorAnio(id, anio);
	}

	@Override
	@Transactional
	public List<Entrega> buscarEntregaRegistroTodos(Long id) {
		return entregaRepository.buscarEntregaRegistroTodos(id);
	}

	@Override
	@Transactional
	public Integer EntregaRegistroPorAnio() {
		return entregaRepository.EntregaRegistroPorAnio();
	}

	@Override
	@Transactional
	public Entrega buscarEntrega(Long id) {
		return entregaRepository.buscarEntrega(id);
	}

	@Override
	@Transactional
	public Boolean camposPlantilla(Long registro_id, String campos) {
		Boolean i = false;
		int existe = 0;
		List<String> camposBase = entregaRepository.camposPlantilla(registro_id);
		int cLong, cbLong;

		String[] camposArchivo = campos.split(",");
		for (String element : camposArchivo) {
			System.out.println(element);
		}

		cLong = camposArchivo.length;
		System.out.println("cLong:" + cLong);
		cbLong = camposBase.size();
		System.out.println("cbLong:" + cbLong);
		if (cLong == cbLong) {
			for (String campoB : camposBase) // SE ITERAN TODOS LOS CAMPOS DE PLANTILLA PARA COMPARAR CON LOS DEL //
												// ARCHIVO
			{
				campoB = campoB.toLowerCase();
				campos = campos.toLowerCase();
				if (campos.contains(campoB)) {
					i = true;
				} else {
					i = false;
					break;
				}
			}
		}
		return i;
	}

	@Override
	@Transactional
	public Entrega entregaPorId(Long id) {

		return entregaRepository.buscarEntregaPorId(id);
	}

	@Override
	@Transactional
	public void borrarDatosEntrega(Long registro_id, Long entrega_id) {
		Registro registro  = registroRepositoryImpl.buscarRegistroPorId(registro_id);
		Entrega entrega = entregaService.buscarEntrega(entrega_id);
		Long idArchivo = 0L; 
		String nombreTabla = null;
		if(registro !=null) {
			nombreTabla = registro.getCodigo();
			if(!tablaRepository.existsTable(nombreTabla).isEmpty()) {
				entregaRepository.borrarDatosEntrega(registro_id, entrega_id);
			}
				
		}
		if(entrega!=null && entrega.getArchivo()!=null) {
			idArchivo = entrega.getArchivo().getId();
			tablaRepository.actualizarEstados(registro.getCodigo(), idArchivo);					
		}

	}

	// FILTROS INDIVIDUALES
	@Override
	@Transactional
	public List<Entrega> findByInstitucion(String institucion) {
		return entregaRepository.findByInstitucion(institucion);
	}

	@Override
	@Transactional
	public List<Entrega> findByTipo(String tiporegistro) {
		return entregaRepository.findByTipo(tiporegistro);
	}

	@Override
	@Transactional
	public List<Entrega> findByPeriodicidad(String periodicidad) {
		return entregaRepository.findByPeriodicidad(periodicidad);
	}

	// FILTROS COMBINADOS LOS TRES
	@Override
	@Transactional
	public List<Entrega> findByInstTipoPerio(String institucion, String tiporegistro, String periodicidad) {
		return entregaRepository.findByInstTipoPerio(institucion, tiporegistro, periodicidad);
	}

	@Override
	@Transactional
	public Long buscarPeriodo(Long id) {
		return entregaRepository.buscarPeriodo(id);
	}

	@Override
	@Transactional
	public ArrayList<Map<String, String>> leerDatosTxtCsvParaInsert(String ubicacion, Registro registro, int tipo) {
		List<Map<String, String>> datos = new ArrayList<Map<String, String>>();// para pasarle los dato
		Map<String, String> mapaParaFila = null;

		// List<String> columnasExcel = leerColumnasExcelDelServidor(ubicacion);
		List<String> columnasExcel = entregaRepository.camposPlantilla(registro.getId());
		int numeroColumnas = columnasExcel.size();
		DataFormatter dataFormatter = new DataFormatter();
		List<String> lines = Collections.emptyList();
		switch (tipo) {
		case 2:

			System.out.println("Leyendo TXT");
			try {
				/*
				 * lines = Files.readAllLines(Paths.get(ubicacion), StandardCharsets.UTF_8);
				 * System.out.println(lines.get(0));
				 */
				System.out.println(ubicacion);
				BufferedReader reader = new BufferedReader(new FileReader(ubicacion));
				// System.out.println(reader.readLine());
				String linea;
				List<String> lineas;
				int i = 0;
				while ((linea = reader.readLine()) != null) {
					mapaParaFila = new HashMap<String, String>();
					System.out.println("p1");
					lineas = Arrays.asList(linea.split(","));
					System.out.println("p3");
					for (int j = 0; j < numeroColumnas; j++) {
						System.out.println(lineas.get(j));
						String dato = lineas.get(j);
						mapaParaFila.put(columnasExcel.get(j), dato);
					}
					datos.add(mapaParaFila);

				}

				reader.close();

			} catch (IOException e) {

				e.printStackTrace();
			}

			break;

		case 3:

			break;
		}

		return (ArrayList<Map<String, String>>) datos;

	}

	@Override
	@Transactional
	public void actualizar(Entrega entrega) {
		entregaRepository.save(entrega);

	}

	@Override
	public void establecerRutaArchivoEntregado(String ubicacion, Long idEntrega) {
		entregaRepository.establecerRutaArchivoEntregado(ubicacion, idEntrega);

	}

	@Override
	public Map<String, Object> realizarEntregaArchivo(MultipartFile file, Long id_periodo, Long id_entrega,
			Long id_registro, Integer anio, Boolean esSustitucion) {
		Long idArchivo = 0L; 
		Registro registro  =  new Registro();
		Map<String, Object> resultados = new HashMap<>();
		Boolean iguales = false;
		Entrega entregaRegistro = cargaDbfComponent.recuperarEntega(id_entrega);
		
		Entrega entregaVersionPlantilla = new Entrega();
		// StringBuilder fileNames = new StringBuilder();
		String extension =  ficherosServices.recuperarExtensionArchivo(file);
		int f = 0;
		String codigo = entregaService.Fn_EntregaArchivoNombre((long) id_registro, (long) id_periodo);
		String directorio = (entregaService.directorio()).trim();
		String directorioN = (directorio + "\\" + entregaRegistro.getRegistro().getUbicacion() + "\\" + codigo).trim();
		String directorioToSave = (directorio + "\\" + entregaRegistro.getRegistro().getUbicacion()).replace('/', '\\')
				.trim();
		directorioN = (directorioN + "." + extension);
		String formatoExcel ="";
		String ubicacion ="";
		
		/*En caso de que las columnas no coicidan además realizar: 
		 * 1. Quitarle fecha de entrega a la entrega con parametros (id_registro, id_periodo)
		 * 2. Ponerle a NUll la ubicacion*/
		switch (extension) {// SE IDENTIFICA FORMATO DEL ARCHIVO CARGADO

		case "xls":
			f = 1;
			formatoExcel = "xls";
			break;
		case "xlsx":
			f = 1;
			formatoExcel =  "xlsx";
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

		if (f > 0) {
			Entrega entregaDb  = new Entrega();
			entregaDb = entregaService.entregaPorId(id_entrega);
			entregaVersionPlantilla = entregaDb;
			codigo = codigo + "." + extension;
			Path fileNameAndPath = Paths.get(directorioToSave, codigo);
			try {
				Files.write(fileNameAndPath, file.getBytes());
				ubicacion = fileNameAndPath.toRealPath().toString();
				entregaService.establecerRutaArchivoEntregado(ubicacion, id_entrega);
				//FileSystemException, esto cuando se esta usando el archivo que se quiere subir

			} catch (IOException e) {
				e.printStackTrace();
			}
			switch (f) {
			case 1:
				f = 1;
				iguales = archivoExcelService.validarColumnasExcel(directorioN, entregaDb.getRegistro(), formatoExcel);
				break;
			case 2:
				iguales = archivoTxtService.validarColumnasTxt(directorioN, entregaDb.getRegistro(), 2);
				f = 2;
				break;
			case 3:
				iguales = archivoTxtService.validarColumnasTxt(directorioN, entregaDb.getRegistro(), 3);
				break;
			case 4:
				break;
			case 5:
				f = 5;
				iguales = archivoSpssService.validarColumnasSpss(directorioN, entregaDb.getRegistro());
				break;
			case 6:
				f = 6;
				iguales = archivoDbfService.validarColumnasDbf(directorioN, entregaDb.getRegistro());
				break;
			default:
				iguales = false;

			}
			
			if (esSustitucion == true) {
				registro  = registroRepositoryImpl.buscarRegistroPorId(id_registro);
				if(entregaRegistro.getArchivo()!=null) {
					idArchivo = entregaRegistro.getArchivo().getId();
					tablaRepository.actualizarEstados(registro.getCodigo(), idArchivo);					
				}

			}

		} /* Resultados */
		resultados.put("iguales", iguales);
		resultados.put("directorioN", directorioN);
		resultados.put("entregaVersionPlantilla", entregaVersionPlantilla);
		return resultados;

	}

	@Override
	public void setFalsoEstadoEntrega(Long id) {
		entregaRepository.setFalsoEstadoEntrega(id);

	}

	@Override
	@Transactional
	public Map<String, Object> asignarVersionPlantillaUsadaParaSubirArchivo(Entrega entrega) {
		VersionPlantilla versionPlantillaActiva = versionPlantillaServiceImpl.recuperarVersionPlantillaHabilitada
				(entrega.getRegistro().getId());
		entregaService.colocarVersionPlantilla(entrega.getId(), versionPlantillaActiva.getId());
		return null;
	}

}
