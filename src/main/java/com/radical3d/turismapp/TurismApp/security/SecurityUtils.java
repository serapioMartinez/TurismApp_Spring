package com.radical3d.turismapp.TurismApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.radical3d.turismapp.TurismApp.repository.ICityRepository;

@Component
public class SecurityUtils {

    @Autowired
    ICityRepository cityRepository;
    
    public int getcityIDForAuthenticatedUser() {
        return cityRepository
                    .findCityIDByUsername(
                        getUserDetailsFromSecurityContext().getUsername())
                    .get();
    }

    private UserDetails getUserDetailsFromSecurityContext(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
