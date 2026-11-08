package com.fluffyyarn.store.security.application.port.in;

import java.time.Instant;

public interface TokenBlocklistPort {

  void blocklist(String jti, Instant expiresAt);

  boolean isBlocklisted(String jti);
}