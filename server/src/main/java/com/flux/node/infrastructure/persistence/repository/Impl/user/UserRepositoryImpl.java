package com.flux.node.infrastructure.persistence.repository.Impl.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.flux.node.domain.model.User;
import com.flux.node.domain.repository.UserRepository;
import com.flux.node.infrastructure.persistence.entity.UserEntity;
import com.flux.node.infrastructure.persistence.mapper.UserMapper;
import com.flux.node.infrastructure.persistence.repository.JPA.user.JPAUserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JPAUserRepository jpaUserRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entityOpt = jpaUserRepository.findByEmail(email);
        return entityOpt.map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> entityOpt = jpaUserRepository.findById(id);
        return entityOpt.map(UserMapper::toDomain);
    }
    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> entityOpt = jpaUserRepository.findByUsername(username);
        return entityOpt.map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserMapper.toEntity(user);
        
        UserEntity saved = jpaUserRepository.save(entity);
        
        return UserMapper.toDomain(saved);
    }
}
