package com.radical3d.turismapp.TurismApp.controller.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {  

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody String entity) {
        //TODO: process POST request
        
        return ResponseEntity.ok(AuthResponse.builder().message("This is the login endpoint").build());
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody(required = true) RegisterRequest register) {
        //TODO: process POST request
        
        return ResponseEntity.ok(AuthResponse.builder().message("This is the register endpoint").build());
    }
    

}
