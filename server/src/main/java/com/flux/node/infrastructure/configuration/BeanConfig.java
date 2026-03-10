package com.flux.node.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flux.node.application.port.RefreshTokenRepository;
import com.flux.node.application.port.TokenService;
import com.flux.node.application.usecase.auth.GetMeUseCase;
import com.flux.node.application.usecase.auth.LoginUseCase;
import com.flux.node.application.usecase.auth.RefreshUseCase;
import com.flux.node.application.usecase.auth.RegisterUseCase;
import com.flux.node.application.usecase.shipment.ShipmentUseCase;
import com.flux.node.domain.repository.ShipmentRepository;
import com.flux.node.domain.repository.UserRepository;


@Configuration
public class BeanConfig {
    @Bean
    public RegisterUseCase registerUseCase(UserRepository repo, PasswordEncoder encoder) {
        return new RegisterUseCase(repo, encoder);
    }
    @Bean
    public LoginUseCase loginUseCase(UserRepository repo, PasswordEncoder encoder, TokenService tokenService, RefreshTokenRepository refreshTokenRepository,
        @Value("${jwt.refresh-token-expiration}") long refreshTokenDuration
    ) {
        return new LoginUseCase(repo, encoder, tokenService, refreshTokenRepository, refreshTokenDuration);
    }

    @Bean
    public GetMeUseCase getMeUseCase(UserRepository repo) {
        return new GetMeUseCase(repo);
     }
    @Bean
    public RefreshUseCase refreshUseCase(RefreshTokenRepository refreshTokenRepository, TokenService tokenService,
        @Value("${jwt.refresh-token-expiration}") long refreshTokenDuration
    ) {
        return new RefreshUseCase(refreshTokenRepository, tokenService, refreshTokenDuration);
    }

    @Bean
    public ShipmentUseCase shipmentUseCase(ShipmentRepository repo) {
        return new ShipmentUseCase(repo);
    }
}
