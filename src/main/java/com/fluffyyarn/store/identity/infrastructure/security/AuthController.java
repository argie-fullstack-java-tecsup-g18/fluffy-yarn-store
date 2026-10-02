package com.fluffyyarn.store.identity.infrastructure.security;

import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  public record LoginRequestDto(
      @NotBlank(message = "El username es obligatorio") String username,
      @NotBlank(message = "El password es obligatorio") String password) {
  }

  public record TokenResponseDto(String token, String tokenType, long expiresIn) {
  }

  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;

  public AuthController(
      TokenService tokenService,
      AuthenticationManager authenticationManager
  ) {
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/login")
  public TokenResponseDto login(@RequestBody @Validated LoginRequestDto request) {
    Authentication auth = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken.unauthenticated(
            request.username(),
            request.password()
        ));

    return new TokenResponseDto(
        tokenService.generate(auth),
        "Bearer",
        TokenService.EXPIRES_IN_SECONDS
    );
  }
}