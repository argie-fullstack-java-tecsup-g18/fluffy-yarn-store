package com.fluffyyarn.store.security.application.port.in;

import org.springframework.security.core.Authentication;

public interface TokenGeneratorPort {

  String generate(Authentication auth);
}
