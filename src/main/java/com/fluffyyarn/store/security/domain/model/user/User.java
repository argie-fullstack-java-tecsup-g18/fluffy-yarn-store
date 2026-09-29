package com.fluffyyarn.store.security.domain.model.user;

import com.fluffyyarn.store.security.domain.model.role.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter // Lombok - Genera los getters de todos los atributos.
@Setter // Lombok - Genera los setters de todos los atributos.
@NoArgsConstructor // Lombok - Genera el constructor vacío (sin parámetros).
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder
// Lombok - Permite crear objetos de dominio con un patrón Builder de forma limpia y fluida. Útil en Arquitectura Hexagonal.
public class User {
  private Integer id; // TODO: Preguntar: por qué el profe usa LONG?
  private String username;
  private String password;
  private Boolean isEnabled;
  private Role role; // Para la FK
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
