package com.ms1.gestionUsuarios.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class JwtRequestDTO implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	
	private String email;
	private String password;
	
	public JwtRequestDTO() {}

	public JwtRequestDTO(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}