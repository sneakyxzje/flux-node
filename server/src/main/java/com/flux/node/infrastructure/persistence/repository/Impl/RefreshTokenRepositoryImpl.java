package com.flux.node.infrastructure.persistence.repository.Impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.flux.node.application.port.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final StringRedisTemplate redisTemplate;
    private final String key = "rt:";
    @Override
    public void save(String refreshToken, Long userId, String sId, String jTi, long duration) {
        String key = this.key + refreshToken;
        redisTemplate.opsForHash().put(key, "userId", userId.toString());
        redisTemplate.opsForHash().put(key, "sessionId", sId);
        redisTemplate.opsForHash().put(key, "jTi", jTi);
        redisTemplate.expire(key, duration, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(String refreshToken) {
        redisTemplate.delete(this.key + refreshToken);
    }
}
