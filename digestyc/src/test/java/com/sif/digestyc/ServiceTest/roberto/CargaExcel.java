package com.sif.digestyc.ServiceTest.roberto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.sif.digestyc.Entity.LoadModule.Archivo;
import com.sif.digestyc.Entity.LoadModule.ColumnaOrigen;
import com.sif.digestyc.Entity.LoadModule.DatoOrigen;
import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.FilaOrigen;
import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Service.Load.ArchivoExcelService;
import com.sif.digestyc.Service.Load.ArchivoService;
import com.sif.digestyc.Service.Load.CargarArchivoService;
import com.sif.digestyc.Service.Load.EntregaService;
import com.sif.digestyc.Service.Load.RegistroService;
import com.sif.digestyc.Service.Load.TablaService;

@SpringBootTest
public class CargaExcel {

	private static final Logger LOG=  LoggerFactory.getLogger(CargaExcel.class);
	
	@Autowired
	@Qualifier("archivoExcelService")
	private ArchivoExcelService archivoExcel;
	
	@Autowired
	@Qualifier("registroServiceImpl")
	private RegistroService registroServiceImpl;
	
	@Autowired
	@Qualifier("ArchivoServiceImpl")
	private ArchivoService archivoServiceImpl;
	
	
	@Autowired
	@Qualifier("cargarArchivoServiceImpl")
	private CargarArchivoService cargarArchivoService;
	
	@Autowired
	@Qualifier("EntregaServiceImpl")
	private EntregaService entregaService;
	
	@Autowired
	@Qualifier("tablaServiceImpl")
	private TablaService tablaServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/*@Test
	@DisplayName("Prueba Servicio de Carga de datos Excel")
	public void leerColumnasDb() {
		String ubicacion = "C://instituciones1/prueba.xlsx";
		Registro registro  = registroServiceImpl.buscarRegistroConColumnas(1L);
		boolean res = archivoExcel.validarColumnasExcel(ubicacion, registro); 
		LOG.info("RESULTADO " + res);
		
	}*/
	
	@Test
	@DisplayName("Inserción de datos")
	public void insercionDatos() {
		String ubicacion = "C://instituciones1/prueba.xlsx";
		Registro registro  = registroServiceImpl.buscarRegistroConColumnas(1L);
		List<Map<String, String>> datos=  new ArrayList<Map<String,String>>();
		//datos= archivoExcel.leerDatosExcelParaInsercion(ubicacion,registro);
		//Esta es la entrega que voy a verificar en el View
		Entrega entrega =  entregaService.entregaPorId(1L);
		Optional<Tabla> tabla = tablaServiceImpl.buscar(1L);
		LOG.info("LA ENTREGA ES  : " + entrega.getId());
		//en base a la entrega crear Archivo
		/*List<String> columnasString = archivoExcel.leerColumnasExcelDelServidor(ubicacion);
		List<ColumnaOrigen> columnas = new ArrayList<ColumnaOrigen>();
		int i=0;
		for(String s : columnasString) {
			ColumnaOrigen columnaOrigen =  new ColumnaOrigen();
			columnaOrigen.setCodigo(s);
			columnaOrigen.setOrden(i++);
			columnaOrigen.setNombre(otro);
			columnaOrigen.setTabla(tabla.get());
			columnas.add(columnaOrigen);
			
		}
		tabla.get().setColumnas(columnas);
		tablaServiceImpl.actualizar(tabla.get());*/
		
		try {
			LOG.info("ANTES DEL BULK");
			cargarArchivoService.insertarConBulk(tabla.get(), (ArrayList<Map<String, String>>) datos);
			LOG.info("DESPUES DEL BULK");
		} catch (SQLException e) {
			e.printStackTrace();
	
}
	
	/**Este es para guardar un archivo, colocarle su ubicacion y su formato 
	 * PreRequisitos: Directorio y Formato
	 * PostResultado :  Creacion de Archivo y Tabla*/
	/*@Test
	@DisplayName("Inserción de datos")
	public void insercionDatos() {

		//1. Necesitamos el ID de la entrega
		LOG.info("ANTES DE RECUPERAR LA ENTREGA ");
		Entrega entrega = entregaService.entregaPorId(1L);
		LOG.info("LA ENTREGA ES  : " + entrega.getId());
		//2. en base a la entrega crear Archivo	
		List<Tabla> tablas = new ArrayList<Tabla>();
		Tabla tabla=  new Tabla(); //Este se debe recuperar 
		tabla.setNombre("TABLA1");	
		tablas.add(tabla);
		LOG.info("PUNTO 1");
		Formato formato = new Formato(); 
		formato.setId(1L);
		//3. Directorio
		Directorio directorio =  new Directorio();
		directorio.setId(1L);
		LOG.info("PUNTO 2");
		Archivo archivo =  new Archivo();
		//DATOS DEL ARCHIVO
		archivo.setCodigo("001");
		archivo.setNombre("PRUEBA1");
		archivo.setFechaSubido(new Date());
		//COmpletos los obligatorios
		LOG.info("PUNTO 3");
		
		archivo.setTablas(tablas);
		tabla.setArchivo(archivo);//Como los quiero crear en el momento asolo loas agrego, las necesarias
		LOG.info("PUNTO 4");
		archivo.setEntrega(entrega);
		entrega.setArchivo(archivo);
		LOG.info("PUNTO 5");
		archivo.setDirectorio(directorio);
		LOG.info("PUNTO 5.1");
		//directorio.setArchivo(archivo); estos no es necesario setearlos del otro lado porque se supone que son registros que ya existen
		LOG.info("PUNTO 6");
		archivo.setFormato(formato);	
		LOG.info("PUNTO 6.1");
		//formato.setArchivo(archivo);  //Este es lo mismo que con directorio

		LOG.info("Antes de guardar archivo");
		archivoServiceImpl.guardarArchivo(archivo);
		tabla.setArchivo(archivo);
		tablaServiceImpl.actualizar(tabla);
		LOG.info("ID DEL ARCHIVO GUARDADO " + archivo.getId());
		
		LOG.info("Guardado con exito");

		
	}*/
	}
}
		

				


