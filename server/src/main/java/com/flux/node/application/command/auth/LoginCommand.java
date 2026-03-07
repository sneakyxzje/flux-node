package com.flux.node.application.command.auth;

public record LoginCommand(
    String identifier,
    String password
) {
    
}
