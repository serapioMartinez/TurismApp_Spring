package com.radical3d.turismapp.TurismApp.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.controller.security.AuthResponse;
import com.radical3d.turismapp.TurismApp.controller.security.RegisterRequest;
import com.radical3d.turismapp.TurismApp.model.security.Role;
import com.radical3d.turismapp.TurismApp.model.security.User;
import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;
import com.radical3d.turismapp.TurismApp.repository.security.IUserAdminRepository;
import com.radical3d.turismapp.TurismApp.repository.security.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserAdminRepository userAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse registerUser(RegisterRequest registerRequest){
        User user = userRepository.findByUsername(registerRequest.getUsername())
                                    .orElse(null);
        if (user != null)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User already exists in records!");
            
        user = User.builder()
                        .username(registerRequest.getUsername())
                        .enabled(registerRequest.isEnabled())
                        .password(passwordEncoder.encode(registerRequest.getPassword()))
                        .role(registerRequest.getUserType()=='C'?Role.ROLE_CITY:Role.ROLE_BUSSINESS)
                        .build();
        
        user = userRepository.save(user);

        UserAdmin userAdmin = UserAdmin.builder()
                                    .user(user)
                                    .name(registerRequest.getName())
                                    .mail(registerRequest.getMail())
                                    .position(registerRequest.getPosition())
                                    .userType(registerRequest.getUserType())
                                    .build();
        userAdmin = userAdminRepository.save(userAdmin);

        return AuthResponse.builder()
                            .username(user.getUsername())
                            .userType(userAdmin.getUserType())
                            .message("User Created Succesfully. Please log into the platform to start!")
                            .build();
    }

}
