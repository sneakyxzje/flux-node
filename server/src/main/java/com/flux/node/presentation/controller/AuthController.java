package com.flux.node.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flux.node.application.command.LoginCommand;
import com.flux.node.application.command.RegisterCommand;
import com.flux.node.application.result.LoginResult;
import com.flux.node.application.usecase.LoginUseCase;
import com.flux.node.application.usecase.RefreshUseCase;
import com.flux.node.application.usecase.RegisterUseCase;
import com.flux.node.presentation.dto.LoginRequest;
import com.flux.node.presentation.dto.RegisterRequest;
import com.flux.node.presentation.util.CookieUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUseCase registerUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshUseCase refreshUseCase;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        registerUseCase.register(new RegisterCommand(
            request.fullname(),
            request.username(),
            request.email(),
            request.password()
        ));            
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResult result = loginUseCase.login(new LoginCommand(
            request.identifier(),
            request.password()
        ));

        Cookie accessTokenCookie = new Cookie("accessToken", result.accessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(15 * 60);  

        Cookie refreshTokenCookie = new Cookie("refreshToken", result.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); 
        
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtils.extract(request, "refreshToken");

        LoginResult result = refreshUseCase.refresh(refreshToken);

        Cookie accessTokenCookie = new Cookie("accessToken", result.accessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(15 * 60);  

        Cookie refreshTokenCookie = new Cookie("refreshToken", result.refreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); 
        
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok().build();
    }
}
