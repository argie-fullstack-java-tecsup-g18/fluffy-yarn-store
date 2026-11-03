package com.fluffyyarn.store.security.application.port.in;

import org.springframework.security.core.Authentication;

public interface TokenGeneratorPort {

  String generate(Authentication auth);

  // Minutos de vida del token emitido. En la respuesta del login se expone como
  // expiresIn (segundos). Se consulta por el puerto para que el adaptador de
  // entrada (AuthController) no dependa de la implementación concreta.
  long expiresInMinutes();
}
