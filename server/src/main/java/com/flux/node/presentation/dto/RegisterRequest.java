package com.flux.node.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
    @NotNull String fullname,
    @NotNull String username,
    @NotNull String email,
    @NotNull String password
) {
    
}
