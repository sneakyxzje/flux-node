package com.flux.node.application.port;


public interface TokenService {
    String generateAccessToken(String userId, String username, String role, String sId);
    String generateSessionId();
    String generateJti();
    String generateRefreshToken();
    Long getUserIdFromToken(String accessToken);
}

