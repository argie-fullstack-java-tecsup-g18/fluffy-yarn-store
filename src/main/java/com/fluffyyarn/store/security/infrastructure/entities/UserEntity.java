package com.fluffyyarn.store.security.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//Este es el que se comunica con la DB. Aquí si entra Spring Boot.
@Entity(name = "User")
@Table(name = "users")
@Getter // Lombok - Genera los getters.
@Setter // Lombok - Genera los setters que JPA usa para hidratar la entidad.
@NoArgsConstructor // Lombok - Constructor vacío. JPA lo necesita para instanciar la clase por reflexión.
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;
  private String password;

  @ManyToOne // Le dice a JPA que es una relación con FK
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  @Column(name = "is_enabled")
  private Boolean isEnabled;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public UserEntity(
      String username,
      String password,
      RoleEntity role,
      Boolean isEnabled,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.isEnabled = isEnabled;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
