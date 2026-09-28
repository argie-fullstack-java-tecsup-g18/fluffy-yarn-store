package com.fluffyyarn.store.security.infrastructure.entities;

import com.fluffyyarn.store.security.domain.model.RoleName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//Este es el que se comunica con la DB. Aquí si entra Spring Boot.
@Entity(name = "Role")
@Table(name = "roles")
@Getter // Lombok - Genera los getters.
@Setter // Lombok - Genera los setters que JPA usa para hidratar la entidad.
@NoArgsConstructor // Lombok - Constructor vacío. JPA lo necesita para instanciar la clase por reflexión.
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private RoleName name;

  private String description;

  private LocalDateTime createdAt;

  // Constructor sin id: lo usa el mapper para crear la entidad desde el dominio.
  // El id se setea después con setId(), igual que en el ejemplo del profe.
  public RoleEntity(RoleName name, String description, LocalDateTime createdAt) {
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
  }
}
