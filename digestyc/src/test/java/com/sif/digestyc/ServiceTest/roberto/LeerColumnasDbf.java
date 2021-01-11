package com.sif.digestyc.ServiceTest.roberto;

import java.util.List;

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

import com.sif.digestyc.Controller.Carga.CargaDbfController;
import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Entity.LoadModule.Formato;
import com.sif.digestyc.Service.Load.ArchivoDbfService;

@SpringBootTest
public class LeerColumnasDbf {

	private static final Logger LOG = LoggerFactory.getLogger(LeerColumnasDbf.class);

	@Autowired
	@Qualifier("archivoDbfServiceImpl")
	private ArchivoDbfService archivoDbfServiceImpl;

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
		String ubicacion = "C://instituciones1/books.dbf";
		Formato formato  = new Formato();
		formato.setId(1L);
		
		Directorio directorio  = new Directorio();
		directorio.setId(1L);
		List<String> columnas  = archivoDbfServiceImpl.leerColumnasDbfDelServidor(ubicacion);
		for(String col : columnas) {
			LOG.info("COLUMNA " + col);
		}
		

	}
}
