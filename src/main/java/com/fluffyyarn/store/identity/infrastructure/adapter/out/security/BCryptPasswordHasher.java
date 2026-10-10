package com.fluffyyarn.store.identity.infrastructure.adapter.out.security;

import com.fluffyyarn.store.identity.application.port.out.security.PasswordHasherPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Adaptador de salida. Implementa el PasswordHasherPort delegando en el
// PasswordEncoder de Spring Security (BCrypt).
@Component
public class BCryptPasswordHasher implements PasswordHasherPort {

  private final PasswordEncoder passwordEncoder;

  public BCryptPasswordHasher(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public String hash(String rawPassword) {
    return this.passwordEncoder.encode(rawPassword);
  }
}
