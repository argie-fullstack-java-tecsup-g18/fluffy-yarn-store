package com.fluffyyarn.store.security.application.service;

import com.fluffyyarn.store.security.application.port.in.TokenBlocklistPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenBlocklistService implements TokenBlocklistPort {

  private static final String PREFIX = "blocklist:";

  private final StringRedisTemplate redisTemplate;

  public TokenBlocklistService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void blocklist(String jti, Instant expiresAt) {
    long timeToLiveSeconds = ChronoUnit.SECONDS.between(Instant.now(), expiresAt);
    if (timeToLiveSeconds > 0) {
      redisTemplate.opsForValue().set(PREFIX + jti, "true", Duration.ofSeconds(timeToLiveSeconds));
    }
  }

  @Override
  public boolean isBlocklisted(String jti) {
    return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + jti));
  }
}