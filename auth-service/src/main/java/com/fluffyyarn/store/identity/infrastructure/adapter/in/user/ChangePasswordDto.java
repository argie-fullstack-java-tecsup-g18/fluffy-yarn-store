package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Cambio de password. Se identifica al usuario por username y se manda el nuevo.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {

  @NotBlank(message = "El username es obligatorio")
  private String username;

  @NotBlank(message = "El nuevo password es obligatorio")
  @Size(min = 8, message = "El password debe tener al menos 8 caracteres")
  private String password;
}
