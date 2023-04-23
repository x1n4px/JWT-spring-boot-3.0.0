package com.ms1.gestionUsuarios.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordDTO {
    @JsonProperty("email")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
