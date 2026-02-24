package com.flux.node.application.result;

public record LoginResult(
    String accessToken,
    String refreshToken
) {
    
}
