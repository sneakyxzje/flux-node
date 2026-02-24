package com.flux.node.infrastructure.security;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.flux.node.application.port.TokenService;
import com.flux.node.domain.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider implements TokenService {
    
    private final String secret;

    private final long accessTokenExpired;


    public JwtProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.access-token-expiration}") long accessTokenExpired    
    ) {
        this.secret = secret;
        this.accessTokenExpired = accessTokenExpired;
    }
    
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessTokenExpired);
        return Jwts.builder()
        .subject(user.getId().toString())
        .claim("username", user.getUsername())
            .claim("role", user.getRole().name())
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiry))
        .signWith(getSigningKey())
        .compact();
    }

    public String generateRefreshToken(User user) {
        return UUID.randomUUID().toString();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(accessToken);
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String accessToken) {
        Claims claims = getClaims(accessToken);
        return claims.get("username", String.class);
    }

    public Long getUserIdFromToken(String accessToken) {
        Claims claims = getClaims(accessToken);
        return Long.parseLong(claims.getSubject());
    }

    public String getRoleFromToken(String accessToken) {
        Claims claims = getClaims(accessToken);
        return claims.get("role", String.class);
    }

    private Claims getClaims(String accessToken) {
        return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(accessToken)
        .getPayload();
    }
}
