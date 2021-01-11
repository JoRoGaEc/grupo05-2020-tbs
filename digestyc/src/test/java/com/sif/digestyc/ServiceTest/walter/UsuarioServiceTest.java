package com.sif.digestyc.ServiceTest.walter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.sif.digestyc.Entity.Security.Usuario;
import com.sif.digestyc.Service.Security.SecurityImpl.UsuarioServiceImpl;

@SpringBootTest
class UsuarioServiceTest {
	@Autowired
	private UsuarioServiceImpl usuarioService;
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
	
	private Usuario getUser() {
		Usuario user = new Usuario();
		user.setUsername("walter");
		user.setApellido("apellidoTest");
		user.setEmail("EmailTest@gmail.com");
		user.setPassword("EmailTest12");
		user.setNombre("nombreTest");
		return user;
	}
	
	@Test
	@DisplayName("CREATE USER")
	public void createUser() {
		Usuario newUser = usuarioService.update(getUser());
		assertThat(newUser).isNotNull();
		usuarioService.delete(newUser);
	}
	
	@Test
	@DisplayName("FIND USER BY USERNAME")
	public void findByUsername() {
		Usuario user = usuarioService.update(getUser());
		Usuario newUser = usuarioService.findByUsername(user.getUsername());
		assertThat(newUser.getUsername()).isEqualTo(user.getUsername());
		usuarioService.delete(newUser);
	}
	
}
