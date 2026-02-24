package com.flux.node.infrastructure.security;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.flux.node.application.port.TokenService;

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

    public String generateAccessToken(String userId, String username, String role, String sId) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(accessTokenExpired);
        return Jwts.builder()
        .subject(userId)
        .claim("username", username)
            .claim("role", role)
            .claim("sId", sId)
        .issuedAt(Date.from(now))
        .expiration(Date.from(expiry))
        .signWith(getSigningKey())
        .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public String generateJti() {
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
