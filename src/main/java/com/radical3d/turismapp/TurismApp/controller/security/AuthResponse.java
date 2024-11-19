package com.radical3d.turismapp.TurismApp.controller.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @JsonIgnore
    private String token;
    @JsonIgnore
    private String refreshToken;
    private String message;
    private String username;
    private char userType;
}
