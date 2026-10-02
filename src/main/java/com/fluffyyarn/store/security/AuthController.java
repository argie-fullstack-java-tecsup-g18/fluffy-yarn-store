package com.fluffyyarn.store.security;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

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

    return new TokenResponseDto(
        tokenService.generate(auth),
        "Bearer",
        TokenService.EXPIRES_IN_SECONDS
    );
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
