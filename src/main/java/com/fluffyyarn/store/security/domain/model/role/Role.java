package com.fluffyyarn.store.security.domain.model.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter // Lombok - Genera los getters de todos los atributos.
@Setter // Lombok - Genera los setters de todos los atributos.
@NoArgsConstructor // Lombok - Genera el constructor vacío (sin parámetros).
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder // Lombok - Permite crear objetos de dominio con un patrón Builder de forma limpia y fluida. Útil en Arquitectura Hexagonal.
public class Role {
  private Long id;
  private RoleName name;
  private String description;
  private LocalDateTime createdAt;
}
