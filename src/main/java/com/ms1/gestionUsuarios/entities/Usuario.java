package com.ms1.gestionUsuarios.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false, unique = true)
	private String username; //email
	@JsonIgnore
	@Column(nullable = false)
	private String password;

	private String nombre;
	private String apellido1;
	private String apellido2;
	 
	public enum Role{
		RESPONSABLE_AULA,
		VIGILANTE_AULA,
		RESPONSABLE_SEDE,
		CORRECTOR,
		VICERRECTORADO
	}
	
	@Column(nullable = false)
	@ElementCollection(fetch = FetchType.EAGER) @Enumerated(EnumType.STRING)
	private Set<Role> roles = new HashSet<Role>();
	

}