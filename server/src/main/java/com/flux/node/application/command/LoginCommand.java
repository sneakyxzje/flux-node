package com.flux.node.application.command;

public record LoginCommand(
    String identifier,
    String password
) {
    
}
