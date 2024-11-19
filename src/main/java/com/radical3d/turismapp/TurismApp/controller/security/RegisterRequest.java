package com.radical3d.turismapp.TurismApp.controller.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private boolean enabled = true;
    private String name;
    private String mail;
    private String position;
    private String image;
    private char userType = 'C';
}
