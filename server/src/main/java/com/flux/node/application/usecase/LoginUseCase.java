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
    private final long refreshTokenExpiration;

    public LoginUseCase(UserRepository userRepository, PasswordEncoder encoder, TokenService tokenService, RefreshTokenRepository refreshTokenRepository, long refreshTokenExpiration) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }


    public LoginResult login(LoginCommand command) {
        String identifier = command.identifier();

        User user = userRepository.findByEmail(identifier)
        .or(() -> userRepository.findByUsername(identifier))
        .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if(!encoder.matches(command.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String accessToken = tokenService.generateAccessToken(user);
        String refreshToken = tokenService.generateRefreshToken(user);

        refreshTokenRepository.save(refreshToken, user.getId(), refreshTokenExpiration);
        return new LoginResult(
            accessToken,
            refreshToken
        );
    }
}
