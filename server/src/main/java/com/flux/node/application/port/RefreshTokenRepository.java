package com.flux.node.application.port;

public interface RefreshTokenRepository {
    void save(String refreshToken, Long userId, String sId, String jTi, long duration);
    
    void delete(String refreshToken);
}
