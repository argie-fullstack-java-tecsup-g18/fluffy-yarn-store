package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

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

  // Mismo criterio que UserResponseDto: el atributo se llama "enabled" para que
  // Lombok genere isEnabled() y Jackson lo deduzca como "enabled", de modo que
  // atributo, getter y setter compartan nombre implicito y se fusionen en una
  // sola propiedad.
  //
  // Sin esto, un cliente que manda "isEnabled" (el nombre del contrato) no
  // encuentra la propiedad: Jackson ignora el campo desconocido, isEnabled queda
  // en false y el usuario se desactiva. Un 204 que dice exito haciendo justo lo
  // contrario del pedido, que es el peor fallo posible.
  @JsonProperty("isEnabled")
  private boolean enabled;
}