package com.ms1.gestionUsuarios;

import com.ms1.gestionUsuarios.dto.JwtRequestDTO;
import com.ms1.gestionUsuarios.dto.JwtResponseDTO;
import com.ms1.gestionUsuarios.repositories.UsuarioRepository;
import com.ms1.gestionUsuarios.service.JwtUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class GestionUsuariosApplicationTests {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private JwtUserDetailsService usuarioService;
	@Autowired
	private PasswordEncoder bcryptEncoder;
  	@Value(value = "${local.server.port}")
	private int port;

	@Test
	@DisplayName("My Test")
	public void myTest() {
		// Arrange
		int a = 2;
		int b = 3;

		// Act
		int result = a + b;

		// Assert
		Assertions.assertEquals(6, result, "The result should be 5");
		System.out.println("5");
	}




	@Test
	@DisplayName("Test Generar Token")//$\checkmark$
	public void testGenerarToken() {

		//loginData = username, password
		JwtRequestDTO jwtRequest = new JwtRequestDTO("napazo2000", "password1");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<JwtRequestDTO> request = new HttpEntity<>(jwtRequest, headers);
		ResponseEntity<JwtResponseDTO> response = restTemplate.exchange(
				"http://localhost:" + port + "/login",
				HttpMethod.POST,
				request,
				JwtResponseDTO.class
		);
		JwtResponseDTO jwtResponse = response.getBody();
		String token = jwtResponse.getToken();

		assertThat(!token.isEmpty());


	}

}
