package com.sif.digestyc.Controller.Carga;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.sif.digestyc.Entity.LoadModule.Archivo;
import com.sif.digestyc.Entity.LoadModule.ColumnaOrigen;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Service.Load.ArchivoService;
import com.sif.digestyc.Service.Load.ArchivoTxtService;
import com.sif.digestyc.Service.Load.CargarArchivoService;
import com.sif.digestyc.Service.Load.EntregaService;
import com.sif.digestyc.Service.Load.InsertarEnTablaService;
import com.sif.digestyc.Service.Load.PlantillaColumnaService;
import com.sif.digestyc.Service.Load.RegistroService;
import com.sif.digestyc.Service.Load.TablaService;
import com.sif.digestyc.utilities.ConexionSqlServer;

@Component
public class CargaTxtController extends ConexionSqlServer {

	private static final Logger LOG = LoggerFactory.getLogger(CargaDbfController.class);
	@Autowired
	@Qualifier("archivoTxtServiceImpl")
	private ArchivoTxtService archivoTxt;

	@Autowired
	@Qualifier("EntregaServiceImpl")
	private EntregaService entregaService;

	@Autowired
	@Qualifier("ArchivoServiceImpl")
	private ArchivoService archivoServiceImpl;

	@Autowired
	@Qualifier("tablaServiceImpl")
	private TablaService tablaServiceImpl;

	@Autowired
	@Qualifier("cargarArchivoServiceImpl")
	private CargarArchivoService cargarArchivoService;

	@Autowired
	@Qualifier("insertarEnTablaServiceImpl")
	private InsertarEnTablaService insertarEnTablaServiceImpl;

	@Autowired
	@Qualifier("registroServiceImpl")
	private RegistroService registroService;

	@Autowired
	@Qualifier("plantillaColumnaServiceImpl")
	private PlantillaColumnaService plantillaColumnaService;

	// Paso 1. Recuperar la entrega, la cual se envia cuando se recibe el ID
	public Entrega recuperarEntega(Long id) {
		return entregaService.entregaPorId(id);
	}

	public Map<String, String> crearMetadataDeTablaTxt(Long idRegistro, Long idEntrega, String nombreTabla,
			Formato formato, String ubicacion, int tipo, String codigo) {
		Map<String, String> mapErrores = new HashMap<String, String>();
		Registro registro = registroService.buscarRegistroPorIdJpa(idRegistro);
		Entrega entrega = recuperarEntega(idEntrega);
		Archivo archivo = new Archivo();

		if (entrega.getVersionPlantilla() == null) {
			boolean plantillaValida = archivoTxt.validarColumnasTxt(ubicacion, entrega.getRegistro(), tipo);
			if (plantillaValida == true || entrega.getVersionPlantilla() != null) {
				List<Map<String, String>> datos = new ArrayList<Map<String, String>>();
				archivo = entrega.getArchivo();
				Tabla tabla = new Tabla(); // Este se debe recuperar
				tabla.setNombre(nombreTabla); // ENTRADA 1: Nombre de la tabla, puede ser generada
				if (archivo != null) {
					tabla.setArchivo(archivo);
				}
				tabla.setEstado(0);
				tablaServiceImpl.actualizar(tabla);
				List<PlantillaColumna> pColumnas = new ArrayList<PlantillaColumna>();
				List<String> columnas2Table = new ArrayList<String>();
				// ****************************************************************************************************
				if (registro.getEntregas().size() > 0 && registro.getEntregas() != null) {
					pColumnas = plantillaColumnaService.getColumnasDePlantilla(registro.getPlantilla().getId());
					columnas2Table = crearColumnasOrigen(pColumnas, tabla, ubicacion);
				} else {
					columnas2Table = crearColumnasOrigen(tabla, ubicacion);
				}

				pColumnas.forEach(c -> {
					this.addColumn(registro.getCodigo(), c.getCodigo());
				});
				// VOLCADO EN LA BD
				Optional<Tabla> tablaDb = tablaServiceImpl.buscar(tabla.getId());// Para comprobar que la tabla se ha
																					// guardado en la Bd
				datos = archivoTxt.leerDatosTxtParaInsercion(pColumnas, ubicacion, entrega.getRegistro(), 2);
				if (tablaDb != null) {
					insertarEnTablaServiceImpl.insertarConBulk(entrega.getRegistro().getId(), entrega.getId(), datos,
							columnas2Table, tablaDb.get().getNombre());
				}

			} else if (plantillaValida == false) {
				mapErrores.put("plantillaInvalida",
						"Las columnas del archivo que se está intentando cargar, no coinciden con "
								+ "la versión de plantilla que está habilitada.");
			}
		} else {
			List<Map<String, String>> datos = new ArrayList<Map<String, String>>();
			archivo = entrega.getArchivo();
			Tabla tabla = new Tabla(); // Este se debe recuperar
			tabla.setNombre(nombreTabla); // ENTRADA 1: Nombre de la tabla, puede ser generada

			if (archivo != null) {
				tabla.setArchivo(archivo);
			}
			tabla.setEstado(0);
			tablaServiceImpl.actualizar(tabla);
			List<PlantillaColumna> pColumnas = new ArrayList<PlantillaColumna>();
			List<String> columnas2Table = new ArrayList<String>();

			pColumnas = plantillaColumnaService.getColumnasDePlantilla(registro.getPlantilla().getId());
			entrega.getRegistro().getPlantilla().getPlantillaColumnas().forEach(c -> {
				columnas2Table.add(c.getCodigo());

			});

			pColumnas.forEach(c -> {
				this.addColumn(registro.getCodigo(), c.getCodigo());
			});
			Optional<Tabla> tablaDb = tablaServiceImpl.buscar(tabla.getId());// Para comprobar que la tabla se ha
																				// guardado en la Bd
			datos = archivoTxt.leerDatosTxtParaInsercion(pColumnas, ubicacion, entrega.getRegistro(), 2);
			if (tablaDb != null) {
				insertarEnTablaServiceImpl.insertarConBulk(entrega.getRegistro().getId(), entrega.getId(), datos,
						columnas2Table, tablaDb.get().getNombre());
			}
		}

		return mapErrores;
	}

	// Crear tabla Columna_Origen
	public List<String> crearColumnasOrigen(Tabla tabla, String ubicacion) {
		List<ColumnaOrigen> columnas = new ArrayList<ColumnaOrigen>();
		Optional<Tabla> tablaDb = tablaServiceImpl.buscar(tabla.getId());

		// Esto para crear ColumnaOrigen por cada columna del archivo
		List<String> columnasString = archivoTxt.leerColumnasTxtDelServidor(ubicacion); // Recuperar las columnas del
																						// archivo

		// Esto porque ya se comprobo que son validos
		int i = 0;
		for (String s : columnasString) {
			ColumnaOrigen columnaOrigen = new ColumnaOrigen();
			columnaOrigen.setCodigo(s);// Este se ocupa para la validación
			columnaOrigen.setOrden(i++);
			columnaOrigen.setNombre(s);
			columnaOrigen.setTabla(tablaDb.get());
			columnas.add(columnaOrigen);

		}
		tablaDb.get().setColumnas(columnas);
		tablaServiceImpl.actualizar(tablaDb.get());
		return columnasString;
	}

	public List<String> crearColumnasOrigen(List<PlantillaColumna> pColumnas, Tabla tabla, String ubicacion) {
		List<ColumnaOrigen> columnas = new ArrayList<ColumnaOrigen>();
		Optional<Tabla> tablaDb = tablaServiceImpl.buscar(tabla.getId());// Para comprobar que la tabla se ha guardado
																			// en la Bd
		List<String> columnasString = archivoTxt.leerColumnasTxtDelServidor(ubicacion);// Esto para crear una
																						// ColumnaOrigen por cada
																						// columna del excel
		// Esto porque ya se comprobo que son validos
		List<String> pColumnasString = new ArrayList<String>();
		LOG.info("COLUMNAS EN CARGAEXCEL ");
		for (PlantillaColumna pc : pColumnas) {
			LOG.info("COLUMNA------" + pc.getNombre());
			pColumnasString.add(pc.getNombre());
		}
		int i = 0;
		for (String s : columnasString) {
			if (!pColumnasString.contains(s)) {
				ColumnaOrigen columnaOrigen = new ColumnaOrigen();
				columnaOrigen.setCodigo(s.replace(" ", "_"));// Este se ocupa para la validacion
				columnaOrigen.setOrden(i++);
				columnaOrigen.setNombre(s);
				columnaOrigen.setTabla(tablaDb.get());
				columnas.add(columnaOrigen);
			}
		}
		tablaDb.get().setColumnas(columnas);
		tablaServiceImpl.actualizar(tablaDb.get());
		return pColumnasString;
	}

}
