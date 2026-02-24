package com.flux.node.application.port;

import java.util.Optional;

import com.flux.node.application.dto.RefreshTokenData;

public interface RefreshTokenRepository {
    void save(String refreshToken, Long userId,String username, String role, String sId, String jTi, long duration);
    Optional<RefreshTokenData> findByRefreshToken(String refreshToken);    
    void delete(String refreshToken);
}

