package com.flux.node.application.usecase;

import com.flux.node.application.dto.RefreshTokenData;
import com.flux.node.application.port.RefreshTokenRepository;
import com.flux.node.application.port.TokenService;
import com.flux.node.application.result.LoginResult;
import com.flux.node.domain.exception.InvalidCredentialsException;


public class RefreshUseCase {
   
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenDuration;

    public RefreshUseCase(RefreshTokenRepository refreshTokenRepository, TokenService tokenService, long refreshTokenDuration) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenService = tokenService;
        this.refreshTokenDuration = refreshTokenDuration;
    }
    public LoginResult refresh(String refreshToken) {
        RefreshTokenData rtData = refreshTokenRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new InvalidCredentialsException("Invalid refresh token"));
       
        refreshTokenRepository.delete(refreshToken);
        String newSessionId = tokenService.generateSessionId();
        String newJti = tokenService.generateJti();
        String newAccessToken = tokenService.generateAccessToken(rtData.userId(), rtData.username(), rtData.role(), newSessionId);
        String newRefreshToken = tokenService.generateRefreshToken();
        refreshTokenRepository.save(newRefreshToken, Long.valueOf(rtData.userId()), rtData.username(), rtData.role(), newSessionId, newJti, refreshTokenDuration);
        return new LoginResult(newAccessToken, newRefreshToken);
    } 
}
