package com.radical3d.turismapp.TurismApp.security;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private UserDetails getUserDetailsFromSecurityContext(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
