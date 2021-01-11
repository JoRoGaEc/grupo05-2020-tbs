package com.sif.digestyc.ServiceTest.walter;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sif.digestyc.Entity.LoadModule.Archivo;
import com.sif.digestyc.Entity.LoadModule.ColumnaOrigen;
import com.sif.digestyc.Entity.LoadModule.PlantillaColumna;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Entity.LoadModule.TipoDato;
import com.sif.digestyc.Entity.LoadModule.VariacionTipoDato;
import com.sif.digestyc.Service.Load.CargaImpl.CargarArchivoServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.PlantillaColumnaServiceImpl;
import com.sif.digestyc.Service.Load.CargaImpl.TablaServiceImpl;

@SpringBootTest
public class CargaArchivoTest {	
	private static final int COLUMNAS = 30;
	private static final int FILAS = 1000;
	private static final int TABLA_ID = 2;
	private Logger logger = LoggerFactory.getLogger(CargaArchivoTest.class);
	@Autowired
	private CargarArchivoServiceImpl cargaService;	
	@Autowired
	private TablaServiceImpl tablaService;
	@Autowired
	private PlantillaColumnaServiceImpl plantillaColumnaService;
	/*
	@Test
	@DisplayName("CREAR COLUMNAS CON BULK")
	public void insertarConBulk(){
		Date hora = new Date();
		Tabla t = new Tabla();
		t.setArchivo(null);
		t.setNombre("Ejemplo");
		t.setDeAcitive();
		t = tablaService.actualizar(t);
		List<ColumnaOrigen> columnas = new ArrayList<>();
		for(int i=1;i<=COLUMNAS;i++) {
			columnas.add(new ColumnaOrigen(t, "columna_"+i, i, "columna "+i));
		}
		t.setColumnas(columnas);
		t = tablaService.actualizar(t);
		ArrayList<Map<String, String>> datos = new ArrayList<Map<String, String>>();
		for(int i=0;i<FILAS;i++) {
			datos.add(getMap(i));
		}
		boolean value = false;
		try {
			value = cargaService.insertarConBulk(t, datos);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Date finalDate = new Date();
		assertThat(value).isTrue();
		tablaService.eliminar(t);
		logger.warn("inicio: "+hora.getHours()+":"+hora.getMinutes()+":"+hora.getSeconds());
		logger.warn("inicio: "+finalDate.getHours()+":"+finalDate.getMinutes()+":"+finalDate.getSeconds());
	}
	
	
	private Map<String, String> getMap(int i) {
		Map<String, String> valores = new HashMap<String, String>(); 
		int j;
		for(j=1; j<=COLUMNAS; j++) {
			valores.put("columna_"+i, "valor fila="+j +"_columna="+i);
		}
		return valores;
	}
	*/
	
}
