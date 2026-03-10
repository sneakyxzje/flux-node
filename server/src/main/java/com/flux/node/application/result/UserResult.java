package com.flux.node.application.result;

public record UserResult(
    Long id,
    String fullname,
    String username,
    String email,
    String role
) {
    
}
