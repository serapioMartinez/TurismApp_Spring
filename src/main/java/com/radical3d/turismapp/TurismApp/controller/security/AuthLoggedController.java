package com.radical3d.turismapp.TurismApp.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.radical3d.turismapp.TurismApp.service.security.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/user_auth/")
public class AuthLoggedController {

    @Autowired
    private AuthService authService;
    
    @GetMapping(path = "/isUserLogged")
    public ResponseEntity<AuthResponse> isUserLogged() throws InterruptedException {
        return ResponseEntity.ok(authService.getLoggeduserData());
    }

    @DeleteMapping(path = "/logout")
    public ResponseEntity<AuthResponse> clearTokensHttpOnlyCookies(HttpServletResponse response){
        return ResponseEntity.ok(AuthResponse.builder().message("This is the logout endpoint").build());
    }

    @PostMapping(path = "/public/refreshToken")
    public ResponseEntity<AuthResponse> refresh(
                        HttpServletRequest request, 
                        HttpServletResponse response) {
        return ResponseEntity.ok(AuthResponse.builder().message("This is the refreshToken endpoint").build());

    }

}
