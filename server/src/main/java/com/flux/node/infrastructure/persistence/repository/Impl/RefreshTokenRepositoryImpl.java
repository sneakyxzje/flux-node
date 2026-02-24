package com.flux.node.infrastructure.persistence.repository.Impl;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.flux.node.application.port.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(String refreshToken, Long userId, long duration) {
        redisTemplate.opsForValue().set(
            refreshToken,
            userId.toString(),
            Duration.ofSeconds(duration)
        );
    }

    @Override
    public void delete(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }
}
