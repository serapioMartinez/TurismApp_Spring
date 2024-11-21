package com.radical3d.turismapp.TurismApp.controller.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radical3d.turismapp.TurismApp.service.security.AuthService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {  

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(HttpServletResponse response, @RequestBody LoginRequest login) { 
        return ResponseEntity.ok(authService.login(response, login));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody(required = true) RegisterRequest register) {
        
        return ResponseEntity.ok(authService.registerUser(register));
    }
    

}
