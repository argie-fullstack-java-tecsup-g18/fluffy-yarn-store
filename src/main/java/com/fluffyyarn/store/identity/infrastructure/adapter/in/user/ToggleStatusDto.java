package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Activar o desactivar (soft delete) un usuario existente.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToggleStatusDto {

  @NotBlank(message = "El username es obligatorio")
  private String username;

  @JsonProperty("isEnabled")
  @JsonAlias("enabled")
  private boolean enabled;
}