package com.sif.digestyc.ServiceTest.walter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sif.digestyc.Service.Load.CatalogosImpl.DirectorioServiceImpl;

@SpringBootTest
class DirectoryTest {
	
	@Autowired
	private DirectorioServiceImpl directoryService;

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
	void test() {
		boolean resultado = directoryService.crearDirectorio("nueva_ubicacion");
		assertThat(resultado).isTrue();
	}

}
