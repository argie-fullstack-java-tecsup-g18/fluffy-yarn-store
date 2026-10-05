package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Activar o desactivar una cuenta. El usuario sigue apareciendo en los listados:
// lo unico que se bloquea es el login, asi que no es un borrado logico.
// Se identifica al usuario por la path (/users/{id}/status), no por el body.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToggleStatusDto {

  @JsonProperty("isEnabled")
  @JsonAlias("enabled")
  private boolean enabled;
}
