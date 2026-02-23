package com.flux.node.infrastructure.persistence.repository.JPA.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flux.node.infrastructure.persistence.entity.UserEntity;

public interface JPAUserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmail(String email); 
}
