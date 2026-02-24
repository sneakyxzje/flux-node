package com.flux.node.infrastructure.persistence.repository.Impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.flux.node.application.dto.RefreshTokenData;
import com.flux.node.application.port.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    private final StringRedisTemplate redisTemplate;
    private final String key = "rt:";
    @Override
    public void save(String refreshToken, Long userId, String username, String role, String sId, String jTi, long duration) {
        String key = this.key + refreshToken;
        redisTemplate.opsForHash().put(key, "userId", userId.toString());
        redisTemplate.opsForHash().put(key, "username", username);
        redisTemplate.opsForHash().put(key, "role", role);
        redisTemplate.opsForHash().put(key, "sessionId", sId);
        redisTemplate.opsForHash().put(key, "jTi", jTi);
        redisTemplate.expire(key, duration, TimeUnit.MILLISECONDS);
    }

    @Override
    public Optional<RefreshTokenData> findByRefreshToken(String refreshToken) {
        String key = this.key + refreshToken;
        if(redisTemplate.hasKey(key)) {
            String userId = (String) redisTemplate.opsForHash().get(key, "userId");
            String username = (String) redisTemplate.opsForHash().get(key, "username");
            String role = (String) redisTemplate.opsForHash().get(key, "role");
            String sessionId = (String) redisTemplate.opsForHash().get(key, "sessionId");
            String jti = (String) redisTemplate.opsForHash().get(key, "jTi");
            return Optional.of(new RefreshTokenData(userId, username, role, sessionId, jti));
        }
        return Optional.empty();
    }



    @Override
    public void delete(String refreshToken) {
        redisTemplate.delete(this.key + refreshToken);
    }
}
