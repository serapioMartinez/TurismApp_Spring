package com.radical3d.turismapp.TurismApp.security.jwt;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import com.radical3d.turismapp.TurismApp.security.jwt.JWTUtils;
import com.radical3d.turismapp.TurismApp.security.jwt.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
public class JWTUtilsTest {

    private final String USERNAME = "Akagami";
    private final String JWT_REG_EXP = "^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$";

    private final String ACCESS_TOKEN_SECRET = "TESTING#SECRETKEY9876543210-TURISMAPP";
    
    @Mock
    private JwtConfig jwtConfig;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JWTUtils jwtUtils;

    @BeforeEach
    void init(){
        when(jwtConfig.getSecreyKey()).thenReturn(ACCESS_TOKEN_SECRET);

    }

    @Test
    void testGenerateRefreshToken() {
        
        when(jwtConfig.getRefreshTokenLifetime()).thenReturn(18000L); //5 hour

        String token = jwtUtils.generateRefreshToken(USERNAME);    

        assertAll(
            () -> assertNotNull(token),
            () -> assertTrue(token.matches(JWT_REG_EXP))
        );
    }

    @Test
    void testGenerateToken() {
        
        when(jwtConfig.getTokenLifetime()).thenReturn(3600L); //1 hour

        String token = jwtUtils.generateToken(USERNAME);    

        assertAll(
            () -> assertNotNull(token),
            () -> assertTrue(token.matches(JWT_REG_EXP))
        );
    }

    @Test
    void testIsTokenValid_Return_true() {
        
        when(jwtConfig.getTokenLifetime()).thenReturn(3600L); //1 hour
        when(userDetails.getUsername()).thenReturn(USERNAME);

        final String token = jwtUtils.generateToken(USERNAME);

        boolean isValid = jwtUtils.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void testIsTokenValid_Return_false() throws Exception{
        
        when(userDetails.getUsername()).thenReturn(USERNAME);
        when(jwtConfig.getTokenLifetime()).thenReturn(0L);

        final String token = jwtUtils.generateToken(USERNAME);
        Thread.sleep(10);
        boolean isValid = jwtUtils.isTokenValid(token, userDetails);

        assertFalse(isValid);
    }
}
