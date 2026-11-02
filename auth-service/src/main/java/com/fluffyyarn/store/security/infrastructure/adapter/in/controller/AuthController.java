package com.fluffyyarn.store.security.infrastructure.adapter.in.controller;

import com.fluffyyarn.store.security.application.port.in.TokenBlocklistPort;
import com.fluffyyarn.store.security.application.port.in.TokenGeneratorPort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final TokenGeneratorPort tokenGeneratorPort;
  private final AuthenticationManager authenticationManager;
  private final TokenBlocklistPort tokenBlocklistPort;

  public AuthController(
      TokenGeneratorPort tokenGeneratorPort,
      AuthenticationManager authenticationManager,
      TokenBlocklistPort tokenBlocklistPort
  ) {
    this.tokenGeneratorPort = tokenGeneratorPort;
    this.authenticationManager = authenticationManager;
    this.tokenBlocklistPort = tokenBlocklistPort;
  }

  @PostMapping("/login")
  /* Contrato de salida (serializa: código a JSON - RESPONSE).
  Convierte el objeto Java del backend en la respuesta JSON
  que se enviará al cliente HTTP. */
  public TokenResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
    // Este authenticationManager se inyecta desde el SecurityConfig
    Authentication auth = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(
            requestDto.username(),
            requestDto.password()
        ));

    // Este authenticationManager se inyecta desde el SecurityConfig
    return new TokenResponseDto(
        tokenGeneratorPort.generate(auth),
        "Bearer",
        com.fluffyyarn.store.security.application.service.TokenGeneratorService.EXPIRES_IN_SECONDS
    );
  }

  @PostMapping("/logout")
  // la anotación @AuthenticationPrincipal trae el objeto con toda la información del usuario que está conectado
  public void logout(@AuthenticationPrincipal Jwt jwt) {
    String jti = jwt.getClaimAsString("jti");
    Instant expiresAt = jwt.getExpiresAt();
    if (jti != null && expiresAt != null) {
      tokenBlocklistPort.blocklist(jti, expiresAt);
    }
  }

  /* Contrato de entrada payload (deserializa: JSON a código - PAYLOAD)
  para la ruta de inicio de sesión. Convierte el JSON de la petición HTTP
  en un objeto Java en memoria para procesarlo en el backend. */
  public record LoginRequestDto(
      @NotBlank(message = "El username es obligatorio") String username,
      @NotBlank(message = "El password es obligatorio") String password) {
  }

  public record TokenResponseDto(String token, String tokenType, long expiresIn) {
  }
}