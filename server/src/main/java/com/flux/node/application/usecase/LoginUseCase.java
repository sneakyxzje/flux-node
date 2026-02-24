package com.flux.node.application.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.flux.node.application.command.LoginCommand;
import com.flux.node.application.port.RefreshTokenRepository;
import com.flux.node.application.port.TokenService;
import com.flux.node.application.result.LoginResult;
import com.flux.node.domain.exception.InvalidCredentialsException;
import com.flux.node.domain.model.User;
import com.flux.node.domain.repository.UserRepository;

public class LoginUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenDuration;
    public LoginUseCase(UserRepository userRepository, PasswordEncoder encoder, TokenService tokenService, RefreshTokenRepository refreshTokenRepository, long refreshTokenDuration) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenDuration = refreshTokenDuration;
    }

    public LoginResult login(LoginCommand command) {
        String identifier = command.identifier();

        User user = userRepository.findByEmail(identifier)
        .or(() -> userRepository.findByUsername(identifier))
        .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if(!encoder.matches(command.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        String sessionId = tokenService.generateSessionId();
        String jti = tokenService.generateJti();
        String accessToken = tokenService.generateAccessToken(user.getId().toString(), user.getUsername(), user.getRole().name(), sessionId);
        String refreshToken = tokenService.generateRefreshToken();

        refreshTokenRepository.save(refreshToken, user.getId(), user.getUsername(), user.getRole().name(), sessionId, jti, refreshTokenDuration);
        return new LoginResult(
            accessToken,
            refreshToken
        );
    }
}
