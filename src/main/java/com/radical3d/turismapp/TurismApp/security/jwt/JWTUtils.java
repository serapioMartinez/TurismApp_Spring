package com.radical3d.turismapp.TurismApp.security.jwt;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

    @Autowired
    private JwtConfig jwtConfig;

    private String getSecretKey(){
        return jwtConfig.getSecreyKey();
    }

    private long getTokenLifetime(){
        return jwtConfig.getTokenLifetime();
    }

    
    private long getRefreshTokenLifetime(){
        return jwtConfig.getRefreshTokenLifetime();
    }

    public String generateToken(String username) {
        final long token_lifetime_ms = getTokenLifetime() * 1000;
        final Date expiration_date = new Date(
                System.currentTimeMillis() +
                        token_lifetime_ms);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiration_date)
                .signWith(Keys.hmacShaKeyFor(getSecretKey().getBytes()))
                .compact();
    }

    public String generateRefreshToken(String username) {
        final long token_lifetime_ms = getRefreshTokenLifetime() * 1000;
        final Date expiration_date = new Date(
                System.currentTimeMillis() +
                        token_lifetime_ms);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiration_date)
                .signWith(Keys.hmacShaKeyFor(getSecretKey().getBytes()))
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }

    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    private Date getTokenExpirationDate(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getTokenExpirationDate(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (isTokenExpired(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token Expired");
        return getUsernameFromToken(token).equals(userDetails.getUsername());
    }

    private <T> T getClaim(String token, Function<Claims, T> claimFunction) {
        return claimFunction.apply(getTokenClaims(token));
    }

    private Claims getTokenClaims(String token){
        try{
            return Jwts
                        .parser()
                        .verifyWith(Keys.hmacShaKeyFor(getSecretKey().getBytes()))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
        }catch(ExpiredJwtException e){
            return e.getClaims();
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT Token Invalid");
        }
    }
}
