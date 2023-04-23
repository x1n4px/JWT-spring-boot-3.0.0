package com.ms1.gestionUsuarios.controller;


import com.ms1.gestionUsuarios.dto.UsuarioDTO;
import com.ms1.gestionUsuarios.entities.Usuario;
import com.ms1.gestionUsuarios.repositories.UsuarioRepository;
import com.ms1.gestionUsuarios.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
 import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

@RestController
public class UsuarioController {
	@Autowired
	private JwtUserDetailsService usuarioService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PreAuthorize("hasRole('VICERRECTORADO')  OR hasRole('RESPONSABLE_SEDE')")
	@GetMapping({ "/usuarios" })
	public List<UsuarioDTO> obtenerTodosLosUsuarios(UriComponentsBuilder uriBuilder){
		var usuarios = usuarioService.obtenerUsuarios();
		Function<Usuario, UsuarioDTO> mapper = (p ->
				UsuarioDTO.fromProducto(p,
						null));
		return usuarios.stream()
				.map(mapper)
				.toList();
	}

/*
{
    "id":0,
    "username":"juanillo",
    "password":"0000",
    "roles":["VICERRECTORADO"]
}
 */
	@PreAuthorize("hasRole('VICERRECTORADO')  OR hasRole('RESPONSABLE_SEDE')")
	@PostMapping({ "/usuarios" })
	public ResponseEntity<URI> guardarUsuario(@RequestBody UsuarioDTO usuario, UriComponentsBuilder uriBuilder) throws Exception {

		Usuario user = usuario.usuarioC();
		Integer id = usuarioService.aniadirUsuario(user);
		URI location = usuarioUriBuilder(uriBuilder.build()).apply(id);

		return ResponseEntity.created(location).header(HttpHeaders.LOCATION, location.toString()).build();

	}

	@PreAuthorize("hasRole('VICERRECTORADO')")
	@GetMapping({ "/usuarios/{id}" })
	@ResponseStatus(code=HttpStatus.OK)
	public UsuarioDTO obtenerUsuario(@PathVariable Integer id, UriComponentsBuilder uriBuilder) {

		Usuario usuario = usuarioService.obtenerUsuario(id);
		return UsuarioDTO.fromProducto(usuario,
				usuarioUriBuilder(uriBuilder.build()));
	}



	@PreAuthorize("hasRole('VICERRECTORADO')")
	@PutMapping({ "/usuarios/{id}" })
	@ResponseStatus(code=HttpStatus.OK)
	public void modificarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuario){
		Usuario entidadUsuario = usuario.usuarioC();
		entidadUsuario.setId(id);
		usuarioService.actualizarUsuario(entidadUsuario);
	}


	@PreAuthorize("hasRole('VICERRECTORADO')")
	@DeleteMapping({ "/usuarios/{id}" })
	@ResponseStatus(code=HttpStatus.OK)
	public void eliminarUsuario(@PathVariable Integer id) {

		usuarioService.eliminarUsuario(id);
	}





	public static Function<Integer, URI> usuarioUriBuilder(UriComponents uriBuilder) {

		return id -> UriComponentsBuilder.newInstance().uriComponents(uriBuilder).path("/usuarios")
				.path(String.format("/%d", id))
				.build()
				.toUri();
	}

}