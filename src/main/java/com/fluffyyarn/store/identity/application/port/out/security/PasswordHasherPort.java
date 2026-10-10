package com.fluffyyarn.store.identity.application.port.out.security;

// Puerto de salida. La aplicación declara que necesita hashear passwords sin saber con qué algoritmo.
public interface PasswordHasherPort {
  String hash(String rawPassword);
}
