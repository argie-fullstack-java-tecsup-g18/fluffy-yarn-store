package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fluffyyarn.store.security.domain.model.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Omite el password porque el web mapper no requiere el password
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Integer id;
  private String username;
  // El atributo se llama "enabled" a proposito, no "isEnabled".
  //
  // Jackson agrupa atributo, getter y setter por su nombre implicito. Lombok, a
  // partir de un boolean, genera isEnabled(), y Jackson le quita el prefijo "is"
  // para deducir "enabled": ese getter y ese atributo ("isEnabled") ya no
  // comparten nombre, quedan como dos propiedades separadas y el JSON lleva
  // isEnabled y enabled duplicados.
  //
  // Con el atributo en "enabled", Lombok vuelve a generar isEnabled(), Jackson lo
  // deduuce como "enabled" y los tres accesores vuelven a fusionarse en una sola
  // propiedad. El @JsonProperty renombra ese grupo ya unido al contrato original.
  @JsonProperty("isEnabled")
  private boolean enabled;
  private RoleName roleName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}