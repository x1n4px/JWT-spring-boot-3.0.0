package com.ms1.gestionUsuarios;

import com.ms1.gestionUsuarios.controller.LoginController;
import com.ms1.gestionUsuarios.dto.JwtRequestDTO;
import com.ms1.gestionUsuarios.dto.JwtResponseDTO;
import com.ms1.gestionUsuarios.repositories.UsuarioRepository;
import com.ms1.gestionUsuarios.security.JwtUtil;
import com.ms1.gestionUsuarios.service.JwtUserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;

@WebMvcTest(LoginController.class)
@SpringBootTest
@ContextConfiguration(classes = { JwtUtil.class, JwtUserDetailsService.class })
class GestionUsuariosApplicationTests {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private JwtUserDetailsService usuarioService;
	@Autowired
	private PasswordEncoder bcryptEncoder;
  	@Value(value = "${local.server.port}")
	private int port;
	@Autowired
 	private MockMvc mockMvc;
	@Autowired
	private TestRestTemplate restTemplate;

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

	private URI uri(String scheme, String host, int port, String... paths) {
		UriBuilderFactory ubf = new DefaultUriBuilderFactory();
		UriBuilder ub = ubf.builder()
				.scheme(scheme)
				.host(host).port(port);
		for (String path : paths) {
			ub = ub.path(path);
		}
		return ub.build();
	}

	private RequestEntity<Void> get(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.get(uri)
				.accept(MediaType.APPLICATION_JSON)
				.build();
		return peticion;
	}

	private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.delete(uri)
				.build();
		return peticion;
	}

	private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
		URI uri = uri(scheme, host, port, path);
		var peticion = RequestEntity.put(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.body(object);
		return peticion;
	}

	@Test
	@DisplayName("Test Generar Token")//$\checkmark$
	public void testGenerarToken() {
		//endpoint ->  http://localhost:8080/login
		//JwtRequestDTO -> {"email":"napazo2", "password":"0000"}
		//return? -> 200 OK -> "token":""
		JwtRequestDTO jwtRequestDTO = new JwtRequestDTO("napazo2", "0000");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<JwtRequestDTO> request = new HttpEntity<>(jwtRequestDTO, headers);
		ResponseEntity<JwtResponseDTO> response = restTemplate.exchange(
				"http://localhost:"+8080+"/login",
				HttpMethod.POST,
				request,
				JwtResponseDTO.class
		);
		JwtResponseDTO jwtResponseDTO = response.getBody();
		String token = jwtResponseDTO.getToken();
		assertThat(!token.isEmpty());



	}

	private void restTemplate() {
	}

}
