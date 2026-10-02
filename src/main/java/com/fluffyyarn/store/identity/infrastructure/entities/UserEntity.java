package com.fluffyyarn.store.identity.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

  @NotBlank(message = "El username es obligatorio")
  @Column(name = "username", unique = true, nullable = false)
  private String username;

  private String password;

  @ManyToOne // Le dice a JPA que es una relación con FK
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  @Column(name = "is_enabled", nullable = false)
  private boolean isEnabled;

  @CreationTimestamp // Para setear el timestamp.
  private LocalDateTime createdAt;

  @UpdateTimestamp // Para setear el timestamp.
  private LocalDateTime updatedAt;

  public UserEntity(
      String username,
      String password,
      RoleEntity role,
      boolean isEnabled,
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
