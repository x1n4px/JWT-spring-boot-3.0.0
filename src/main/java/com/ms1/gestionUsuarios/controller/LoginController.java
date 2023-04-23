package com.ms1.gestionUsuarios.controller;

import com.ms1.gestionUsuarios.dto.JwtRequestDTO;
import com.ms1.gestionUsuarios.dto.JwtResponseDTO;
import com.ms1.gestionUsuarios.dto.ResetPasswordDTO;
import com.ms1.gestionUsuarios.dto.UsuarioDTO;
import com.ms1.gestionUsuarios.entities.Usuario;
import com.ms1.gestionUsuarios.repositories.UsuarioRepository;
import com.ms1.gestionUsuarios.security.CustomAuthenticationManager;
import com.ms1.gestionUsuarios.security.JwtUtil;
import com.ms1.gestionUsuarios.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;

@RestController
public class LoginController {
	
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private CustomAuthenticationManager authenticationManager;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	/*
	{
		"id": 5,
		"username": "napazo2",
		"password": "0000" ,
		"roles":["VICERRECTORADO"]
	}
	 */
	@PostMapping("/register")
	public ResponseEntity<?> saveUser(@RequestBody UsuarioDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}	

	private void authenticate(String username, String password) throws Exception {
	try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	} catch (DisabledException e) {
		throw new Exception("USER_DISABLED", e);
	} catch (BadCredentialsException e) {
		throw new Exception("INVALID_CREDENTIALS", e);
	}
}
	
	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponseDTO(token));
	}


	@PostMapping("/passwordreset")
	public void resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
		Optional<Usuario> optionalUsuario = userDetailsService.buscarUsername(resetPasswordDTO.getUsername());

		if (optionalUsuario.isPresent()) {
			Usuario usuario = optionalUsuario.get();
			String nuevaContrasena = generateRandomPassword();
			usuario.setPassword(bcryptEncoder.encode(nuevaContrasena));
			usuarioRepository.save(usuario);
			/*
			COMUNICACIÓN CON MICROSERVICIO NOTIFICACIONES
			 */
 		}
	}


	private String generateRandomPassword() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}


}
