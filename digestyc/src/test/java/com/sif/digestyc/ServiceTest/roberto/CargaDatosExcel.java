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

import com.sif.digestyc.Controller.Carga.CargaExcelController;
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
public class CargaDatosExcel {

	private static final Logger LOG = LoggerFactory.getLogger(CargaDatosExcel.class);

	@Autowired
	private CargaExcelController cargaExcelController;

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



	@Test
	@DisplayName("Inserción de datos")
	public void insercionDatos() {
		String ubicacion = "C://directorio2/MINED_R1_P1.xlsx";
		Formato formato  = new Formato();
		formato.setId(1L);		
		Directorio directorio  = new Directorio();
		directorio.setId(1L);
		//cargaExcelController.crearMetadataDeTablaExcel(1L, "TABLA1",formato, ubicacion);
		//cargaExcelController.crearMetadataDeTablaExcel(1L, "TABLA1",formato, directorio, ubicacion);

	}
}
