package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Registro público de un customer.
// No lleva roleName a propósito: el rol lo asigna el sistema (CUSTOMER).
// Si lo aceptáramos, un visitor podría auto-asignarse ADMIN.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {

  @NotBlank(message = "El username es obligatorio")
  @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
  private String username;

  @NotBlank(message = "El password es obligatorio")
  @Size(min = 8, message = "El password debe tener al menos 8 caracteres")
  private String password;
}
