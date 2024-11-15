package com.radical3d.turismapp.TurismApp.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    @Value("${jwt.secret}")
    private String ACCESS_TOKEN_SECRET;
    
    @Value("${jwt.expiration-seconds}")
    public Long LIFETIME_TOKEN_SECONDS;

    @Value("${jwt.refresh.expiration-seconds}")
    public Long LIFETIME_REFRESH_TOKEN_SECONDS;

    public String getSecreyKey() {
        return ACCESS_TOKEN_SECRET;
    }

    public Long getTokenLifetime() {
        return LIFETIME_TOKEN_SECONDS;
    }

    public Long getRefreshTokenLifetime() {
        return LIFETIME_REFRESH_TOKEN_SECONDS;
    }

    
}
