package com.radical3d.turismapp.TurismApp.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JWTUtils {
    private final static String ACCESS_TOKEN_SECRET = "RADICAL#3D-SECRET-PHRASE-FOR-ENCRYPTION";
    public final static Long LIFETIME_TOKEN_SECONDS = 3600L;//1 h
    public final static Long LIFETIME_REFRESH_TOKEN_SECONDS = LIFETIME_TOKEN_SECONDS * 5;

    public static String generateToken(String username){
        final long token_lifetime_ms = LIFETIME_TOKEN_SECONDS * 1000;
        final Date expiration_date 
                        = new Date(
                            System.currentTimeMillis() + 
                            token_lifetime_ms);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiration_date)
                .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                .compact();
    }

    public static String generateRefreshToken(String username){
        final long token_lifetime_ms = LIFETIME_REFRESH_TOKEN_SECONDS * 1000;
        final Date expiration_date 
                        = new Date(
                            System.currentTimeMillis() + 
                            token_lifetime_ms);
        
        return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date())
                    .expiration(expiration_date)
                    .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                    .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(UserDetails userDetails){
        return 
            new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
    }

    public static boolean isTokenValid(String jwtToken){
        return false;
    }

    private static <T> T getClaim(String token, Function<Claims,T> claimFunction){
        Claims cliams = Jwts.parser().decryptWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRET.getBytes()))
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();
        
        return claimFunction.apply(cliams);
    }
}
