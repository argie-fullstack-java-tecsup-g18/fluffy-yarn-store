package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Credenciales para authenticarse contra un usuario de la base.
// Solo necesita identidad y password: nada más.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateUserDto {

  @NotBlank(message = "El username es obligatorio")
  private String username;

  @NotBlank(message = "El password es obligatorio")
  private String password;
}
