package com.flux.node.application.usecase;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.flux.node.application.command.RegisterCommand;
import com.flux.node.domain.enums.Role;
import com.flux.node.domain.model.User;
import com.flux.node.domain.repository.UserRepository;

public class RegisterUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public RegisterUseCase(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void register(RegisterCommand command) {
        String hashed = encoder.encode(command.password());
        User user = new User(
            null,
            command.email(),
            command.username(),
            command.fullname(),
            hashed,
            Role.USER
        );
        userRepository.save(user);
    }
}
