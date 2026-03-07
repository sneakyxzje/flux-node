package com.flux.node.application.usecase.auth;

import com.flux.node.application.result.UserResult;
import com.flux.node.domain.model.User;
import com.flux.node.domain.repository.UserRepository;

public class GetMeUseCase {
    private final UserRepository userRepository;

    public GetMeUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResult getMe(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResult(
            user.getId(),
            user.getFullname(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name()
        );
    }
}
