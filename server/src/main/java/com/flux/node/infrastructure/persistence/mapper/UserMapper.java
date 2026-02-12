package com.flux.node.infrastructure.persistence.mapper;

import com.flux.node.domain.model.User;
import com.flux.node.infrastructure.persistence.entity.UserEntity;

public class UserMapper {
    public static User toDomain(UserEntity userEntity) {
        return new User(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.getUsername(),
            userEntity.getFullname(),
            userEntity.getPassword(),
            userEntity.getRoles()
        );
    }

    public static UserEntity toEntity(User domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        return entity;
    }
}
