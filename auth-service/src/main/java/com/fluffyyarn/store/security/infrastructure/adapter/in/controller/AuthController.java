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
  public TokenResponseDto login(@RequestBody @Valid LoginRequestDto requestDto) {
    Authentication auth = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(
            requestDto.username(),
            requestDto.password()
        ));

    return new TokenResponseDto(
        tokenGeneratorPort.generate(auth),
        "Bearer",
        com.fluffyyarn.store.security.application.service.TokenGeneratorService.EXPIRES_IN_SECONDS
    );
  }

  @PostMapping("/logout")
  public void logout(@AuthenticationPrincipal Jwt jwt) {
    String jti = jwt.getClaimAsString("jti");
    Instant expiresAt = jwt.getExpiresAt();
    if (jti != null && expiresAt != null) {
      tokenBlocklistPort.blocklist(jti, expiresAt);
    }
  }

  public record LoginRequestDto(
      @NotBlank(message = "El username es obligatorio") String username,
      @NotBlank(message = "El password es obligatorio") String password) {
  }

  public record TokenResponseDto(String token, String tokenType, long expiresIn) {
  }
}