package com.flux.node.presentation.dto;

public record LoginRequest(
    String identifier,
    String password
) {
    
}
