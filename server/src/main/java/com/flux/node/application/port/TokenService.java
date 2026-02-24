package com.flux.node.application.port;

import com.flux.node.domain.model.User;

public interface TokenService {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
}
