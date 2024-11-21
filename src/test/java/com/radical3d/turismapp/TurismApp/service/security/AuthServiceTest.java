package com.radical3d.turismapp.TurismApp.service.security;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import com.radical3d.turismapp.TurismApp.controller.security.AuthResponse;
import com.radical3d.turismapp.TurismApp.controller.security.RegisterRequest;
import com.radical3d.turismapp.TurismApp.model.security.Role;
import com.radical3d.turismapp.TurismApp.model.security.User;
import com.radical3d.turismapp.TurismApp.model.security.UserAdmin;
import com.radical3d.turismapp.TurismApp.repository.security.IUserAdminRepository;
import com.radical3d.turismapp.TurismApp.repository.security.IUserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserAdminRepository userAdminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private User user;
    private UserAdmin userAdmin;

    private final String USERNAME = "Akagami";
    private final String PASSWORD = "PASS";

    @BeforeEach
    void init(){
        user = User.builder()
                    .username(USERNAME)
                    .password(PASSWORD)
                    .enabled(true)
                    .role(Role.ROLE_CITY)
                    .build();
        userAdmin = UserAdmin.builder()
                        .user(user)
                        .id(1)
                        .name("Name for User")
                        .position("position")
                        .mail("mail@example.com")
                        .userType('C')
                        .build();
    }

    @Test
    void testRegisterUser_newUser() {
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn(PASSWORD);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(userRepository.save(Mockito.any())).thenReturn(user);

        when(userAdminRepository.save(Mockito.any())).thenReturn(userAdmin);

        RegisterRequest registerRequest = RegisterRequest.builder()
                                            .username(USERNAME)
                                            .userType(userAdmin.getUserType())
                                            .name(userAdmin.getName())
                                            .position(userAdmin.getPosition())
                                            .enabled(user.isEnabled())
                                            .mail(userAdmin.getMail())
                                            .password(user.getPassword())
                                            .build();

        AuthResponse authResponse = authService.registerUser(registerRequest);
        assertAll(
            () -> assertNotNull(authResponse),
            () -> assertEquals(USERNAME, authResponse.getUsername()),
            () -> assertEquals(userAdmin.getUserType(), authResponse.getUserType()),
            () -> assertNotNull(authResponse.getMessage())
        );
    }

    @Test
    void testRegisterUser_existingUser() {

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));


        RegisterRequest registerRequest = RegisterRequest.builder()
                                            .username(USERNAME)
                                            .userType(userAdmin.getUserType())
                                            .name(userAdmin.getName())
                                            .position(userAdmin.getPosition())
                                            .enabled(user.isEnabled())
                                            .mail(userAdmin.getMail())
                                            .password(user.getPassword())
                                            .build();

        assertThrows(
            ResponseStatusException.class, 
            () -> authService.registerUser(registerRequest)
        );
    }
}
