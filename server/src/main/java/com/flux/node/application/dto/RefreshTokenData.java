package com.flux.node.application.dto;

public record RefreshTokenData(
    String userId,
    String username,
    String role,
    String sessionId,
    String jti
) {
    
}
