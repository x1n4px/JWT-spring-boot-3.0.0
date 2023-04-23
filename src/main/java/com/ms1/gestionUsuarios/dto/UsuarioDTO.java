package com.ms1.gestionUsuarios.dto;

import java.io.Serializable;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms1.gestionUsuarios.entities.Usuario;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	@JsonProperty("email")
	private String username;

	private String password;

	private String nombre;
	private String apellido1;
	private String apellido2;
	@JsonIgnore
	private Set<Usuario.Role> roles = new HashSet<Usuario.Role>();

	public static UsuarioDTO fromProducto(Usuario usuario,
										  Function<Integer, URI> productoUriBuilder) {
		var dto = new UsuarioDTO();

		dto.setUsername(usuario.getUsername());
		dto.setRoles(usuario.getRoles());
		dto.setId(usuario.getId());
		dto.setNombre(usuario.getNombre());
		dto.setApellido1(usuario.getApellido1());
		dto.setApellido2(usuario.getApellido2());
		return dto;
	}

	public Usuario usuarioC() {
		var prod = new Usuario();
		prod.setUsername(username);
		prod.setRoles(roles);
		prod.setId(id);
		prod.setNombre(nombre);
		prod.setApellido1(apellido1);
		prod.setApellido2(apellido2);
		return prod;
	}


}