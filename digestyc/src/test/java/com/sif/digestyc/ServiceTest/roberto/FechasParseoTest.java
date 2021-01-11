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
import org.springframework.transaction.annotation.Transactional;

import com.sif.digestyc.Entity.LoadModule.Archivo;
import com.sif.digestyc.Entity.LoadModule.ColumnaOrigen;
import com.sif.digestyc.Entity.LoadModule.ColumnaVersionPlantilla;
import com.sif.digestyc.Entity.LoadModule.DatoOrigen;
import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Entity.LoadModule.Entrega;
import com.sif.digestyc.Entity.LoadModule.FilaOrigen;
import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Entity.LoadModule.Registro;
import com.sif.digestyc.Entity.LoadModule.Tabla;
import com.sif.digestyc.Repository.Carga.ColumnaVersionPlantillaRepository;
import com.sif.digestyc.Service.Estandarizacion.ParsearDataService;
import com.sif.digestyc.Service.Estandarizacion.EstandarizacionImpl.ParsearDataServiceImpl;
import com.sif.digestyc.Service.Load.ArchivoExcelService;
import com.sif.digestyc.Service.Load.ArchivoService;
import com.sif.digestyc.Service.Load.CargarArchivoService;
import com.sif.digestyc.Service.Load.EntregaService;
import com.sif.digestyc.Service.Load.RegistroService;
import com.sif.digestyc.Service.Load.TablaService;

@SpringBootTest
public class FechasParseoTest {

	private static final Logger LOG = LoggerFactory.getLogger(FechasParseoTest.class);


	private ParsearDataServiceImpl parsearDataServive;
	

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
	@DisplayName("PARSEO DATOS")
	public void insercionDatos() {
		parsearDataServive = new ParsearDataServiceImpl();
		//String dato = parsearDataServive.convertirFechaParaSqlServer("11/2000/01", "MM/yyyy/dd", "datetime2");
		//LOG.info("DATO parseado : "+ dato);

	}
}
