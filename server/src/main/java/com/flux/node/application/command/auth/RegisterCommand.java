package com.flux.node.application.command.auth;

public record RegisterCommand(
    String fullname,
    String username,
    String email,
    String password
) {
    
}
