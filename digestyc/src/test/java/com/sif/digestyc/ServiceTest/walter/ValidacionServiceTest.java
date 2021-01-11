package com.sif.digestyc.ServiceTest.walter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sif.digestyc.Service.Validation.ValidationImpl.ValidationServiceImpl;

@SpringBootTest
class ValidacionServiceTest {
	
	@Autowired
	private ValidationServiceImpl validationService;

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
	@DisplayName("VALIDANDO ENTEROS")
	void ValidandoEnteros() {
		assertThat(validationService.isInteger("12", 0, 0, true)).isTrue();
		assertThat(validationService.isInteger("12.23", 0, 0, false)).isFalse();
		assertThat(validationService.isInteger("assd", 0, 0, true)).isFalse();
		assertThat(validationService.isInteger("", 0, 0, false)).isFalse();
		assertThat(validationService.isInteger(null, -25, 12, true)).isTrue();
		assertThat(validationService.isInteger("", 0, 0, true)).isTrue();
		assertThat(validationService.isInteger("12", 1, 13, true)).isTrue();
		assertThat(validationService.isInteger("20", 5, 12, false)).isFalse();
		assertThat(validationService.isInteger("52", 25, 13, true)).isFalse();
		assertThat(validationService.isInteger("", 5, 12, true)).isTrue();
		assertThat(validationService.isInteger("-15", -25, 12, true)).isTrue();
		assertThat(validationService.isInteger(null, -25, 12, false)).isFalse();
	}
	
	
	@Test
	@DisplayName("VALIDANDO FLOAT O DOUBLE")
	void ValidandoFloatOrDouble() {
		assertThat(validationService.isFloatOrDouble("12", 0, 0, true)).isTrue();
		assertThat(validationService.isFloatOrDouble("12.23", 0, 0, false)).isTrue();
		assertThat(validationService.isFloatOrDouble("assd", 0, 0, true)).isFalse();
		assertThat(validationService.isFloatOrDouble("", 0, 0, false)).isFalse();
		assertThat(validationService.isFloatOrDouble("", 0, 0, true)).isTrue();
		assertThat(validationService.isFloatOrDouble("-0.12", -1, 13, true)).isTrue();
		assertThat(validationService.isFloatOrDouble("20.25", 5, 12, false)).isFalse();
		assertThat(validationService.isFloatOrDouble("52.52", 25, 13, true)).isFalse();
		assertThat(validationService.isFloatOrDouble("", 5, 12, true)).isTrue();
		assertThat(validationService.isFloatOrDouble(null, 5, 12, true)).isTrue();
		assertThat(validationService.isFloatOrDouble(null, 5, 12, false)).isFalse();
	}
	
	

	@Test
	@DisplayName("VALIDANDO STRING")
	void ValidandoStringYValores() {
		assertThat(validationService.isString("F", "#,F,,M,,f,,m,", 10, false)).isTrue();
		assertThat(validationService.isString("F", "#,F,,M,,f,,m,", 10, false)).isTrue();
		assertThat(validationService.isString("M", "#,F,,M,,f,,m,", 10, false)).isTrue();
		assertThat(validationService.isString("", "#,F,,M,,f,,m,", 10, true)).isTrue();
		assertThat(validationService.isString("", "#,F,,M,,f,,m,", 10, false)).isFalse();
		assertThat(validationService.isString("Fasfasfa", "#,F,,M,,f,,m,", 1, false)).isFalse();
		assertThat(validationService.isString("H", "#,F,M,,f,,m,", 10, false)).isFalse();
		assertThat(validationService.isString("F", "#,F,,M,,f,,m,", -10, false)).isFalse();
		assertThat(validationService.isString(null, "#,F,,M,,f,,m,", -10, false)).isFalse();
		assertThat(validationService.isString(null, "#,F,,M,,f,,m,", -10, true)).isTrue();
	}
	
	
	

	@Test
	@DisplayName("VALIDANDO FECHAS")
	void ValidandoDates() {
		assertThat(validationService.isDate("29/05/2018", null, null, "dd/MM/yyyy", false)).isTrue();
		assertThat(validationService.isDate("29-05-2018", null, null, "dd-MM-YYYY", false)).isTrue();
		assertThat(validationService.isDate("05-30-2018", null, null, "MM-dd-YYYY", false)).isTrue();
		assertThat(validationService.isDate("2018/05/20", null, null, "YYYY/MM/dd", false)).isTrue();
		assertThat(validationService.isDate("2018-05-20", null, null, "YYYY-MM-dd", false)).isTrue();
		assertThat(validationService.isDate("2018-05-20", null, null, "YYYY-MM-dd hh:mm:ss", false)).isFalse();
		assertThat(validationService.isDate("", null, null, "YYYY-MM-dd", true)).isTrue();
		assertThat(validationService.isDate(null, null, null, "YYYY-MM-dd", true)).isTrue();
		
		
		/*Date fecha = new Date("28-03-2017");
		System.out.println(fecha.toString());*/
		/*
		 * assertThat(validationService.isDate("29/05/2018", null, new Date(), "dd/MM/YYYY", false)).isFalse();
		assertThat(validationService.isDate("29-05-2018", new Date(), null, "dd-MM-YYYY", false)).isFalse();
		assertThat(validationService.isDate("05-30-2018", null, new Date(), "MM-dd-YYYY", false)).isTrue();*/
		
	}
	
	
	

}
