package com.sif.digestyc.ServiceTest.walter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sif.digestyc.Service.Load.CargaImpl.InsertarEnTablaServiceImpl;

@SpringBootTest
class InsertarTablaTest {
	
	private static final int FILAS = 100000;
	private static final int COLUMNAS = 10;
	
	@Autowired
	private InsertarEnTablaServiceImpl insertarTablaService;
	
	private Logger logger = LoggerFactory.getLogger(InsertarTablaTest.class);

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
		List<Map<String, String>> data = new ArrayList<Map<String,String>>();
		for(int i=0;i<FILAS;i++) {
			data.add(getMap(COLUMNAS, i));
		}
		List<String> columnas = getList(COLUMNAS);
		assertThat(insertarTablaService.insertarConBulk((long)2, (long)3, data, columnas, "[tablas].NUEVA_TABLA")).isTrue();
	}
	
	private Map<String, String> getMap(int columna, int fila) {
		Map<String, String> valores = new HashMap<String, String>();
		Random r = new Random();
		int anio = 1920 + r.nextInt(100);
		int mes = 1 + r.nextInt(12);
		int dia = 1 + r.nextInt(24);
		for(int i=0;i<columna;i++) {
			valores.put("columna_"+i, "Columna:"+i+"Fila:"+fila);
		}
		return valores;
	}
	
	private List<String> getList(int columna) {
		List<String> valores = new ArrayList<>();
		for(int i=0;i<columna;i++) {
			valores.add("columna_"+i);
		}
		return valores;
	}

}
