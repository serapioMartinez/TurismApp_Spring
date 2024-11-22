package com.radical3d.turismapp.TurismApp.controller.security;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radical3d.turismapp.TurismApp.security.JwtAuthorizationFilter;
import com.radical3d.turismapp.TurismApp.service.security.AuthService;

@WebMvcTest(
    controllers = AuthController.class,
    excludeFilters = @Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthorizationFilter.class}
    )  
)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private AuthResponse authResponse;

    private final String USERNAME = "Akagami";

    @BeforeEach
    void init(){
        authResponse = AuthResponse.builder()
                                .message("User created")
                                .username(USERNAME)
                                .userType('C')
                                .build();
    }

    @Test
    void testRegister() throws Exception{
        when(authService.registerUser(Mockito.any())).thenReturn(authResponse);

        RegisterRequest registerRequest = RegisterRequest.builder()
                                            .username(USERNAME)
                                            .userType(authResponse.getUserType())
                                            .name("Name for user")
                                            .build();
        String jsonResponse = objectMapper.writeValueAsString(registerRequest);

        ResultActions result = mvc.perform(
            post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonResponse));


        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("username").value(USERNAME));

    }
}
