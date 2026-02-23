package com.flux.node.domain.repository;

import java.util.Optional;

import com.flux.node.domain.model.User;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
