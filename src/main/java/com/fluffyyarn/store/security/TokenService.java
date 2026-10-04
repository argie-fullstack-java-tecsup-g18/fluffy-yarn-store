package com.fluffyyarn.store.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TokenService {

  public static final long EXPIRES_IN_MINUTES = 60;
  public static final long EXPIRES_IN_SECONDS = EXPIRES_IN_MINUTES * 60;

  private final JwtEncoder encoder;

  public TokenService(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  // Genera el token a partir de una autenticacion ya validada.
  public String generate(Authentication auth) {
    Instant now = Instant.now();

    // payload { "iss": "fluffy-yarn-store", "exp": "14134124412", ... }
    // ESto es lo que constituye el cuerpo y contenido del token JWT (JSON Web Token).
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("fluffy-yarn-store")
        .subject(auth.getName())
        .issuedAt(now)
        .expiresAt(now.plus(EXPIRES_IN_MINUTES, ChronoUnit.MINUTES))
        .claim("roles", roles(auth))
        .build();

    // El header dice con qué algoritmo se codificó el token.
    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

    return encoder.encode(JwtEncoderParameters.from(header, claims))
        .getTokenValue();
  }

  // Este mét.odo solo se usa en esta clase por eso es privado
  // Extract real role names and remove the `ROLE_` prefix
  private List<String> roles(Authentication auth) {
    return auth.getAuthorities().stream()
        // Spring Security 7 agrega solo un FactorGrantedAuthority("FACTOR_PASSWORD")
        // a todo login con contraseña: dice con qué factor se autenticó, no es un
        // rol. Si se mapea igual, termina en el claim "roles" y del otro lado el
        // JwtGrantedAuthoritiesConverter lo vuelve ROLE_FACTOR_PASSWORD.
        .filter(authority -> !(authority instanceof FactorGrantedAuthority))
        .map(GrantedAuthority::getAuthority)
        .map(role -> role.replace("ROLE_", ""))
        .toList();
  }
}