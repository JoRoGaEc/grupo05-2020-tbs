package com.sif.digestyc.ServiceTest.bertha;

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

import com.sif.digestyc.Controller.Carga.CargaSpssController;
import com.sif.digestyc.Entity.LoadModule.Directorio;
import com.sif.digestyc.Entity.LoadModule.Formato;

@SpringBootTest
public class CargaDatosSpss {

	private static final Logger LOG = LoggerFactory.getLogger(CargaDatosSpss.class);

	@Autowired
	private CargaSpssController cargaSpssController;

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
		String ubicacion = "C://tesis/pruebaspss.sav";
		Formato formato  = new Formato();
		formato.setId(1L);
		
		Directorio directorio  = new Directorio();
		directorio.setId(1L);
		//cargaSpssController.crearMetadataDeTablaSpss(1L, "TABLASPSS1",formato, ubicacion);
		//cargaSpssController.crearMetadataDeTablaSpss(1L, "TABLASPSS1",formato, directorio, ubicacion);
	}
}
