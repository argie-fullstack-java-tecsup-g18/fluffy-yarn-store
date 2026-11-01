package com.fluffyyarn.store.security;

import com.fluffyyarn.store.security.application.port.in.TokenBlocklistPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

// El servicio que se comunica con redis
@Service
public class TokenBlocklistService implements TokenBlocklistPort {

  // En Redis, los datos se guardan como pares de "clave-valor".
  private static final String PREFIX = "blocklist:";

  // StringRedisTemplate herramienta oficial que provee Spring para leer y escribir textos planos en la DB de Redis
  private final StringRedisTemplate redisTemplate;

  public TokenBlocklistService(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  // Guarda el token prohibido
  // Este metodo se llama unica y exclusivamente cuando el usuario hace log out
  @Override
  public void blocklist(String jti, Instant expiresAt) {
    long timeToLiveSeconds = ChronoUnit.SECONDS.between(Instant.now(), expiresAt);
    if (timeToLiveSeconds > 0) {
      // Aqui se establece también el ttl y al expirarse el ttl se borra el registro de redis
      redisTemplate.opsForValue().set(PREFIX + jti, "true", Duration.ofSeconds(timeToLiveSeconds));
    }
  }

  @Override
  public boolean isBlocklisted(String jti) {
    // Valida si existe el registro de jti
    return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + jti));
  }
}
