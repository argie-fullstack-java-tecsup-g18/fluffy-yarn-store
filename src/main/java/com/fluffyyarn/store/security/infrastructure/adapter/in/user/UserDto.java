package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

import com.fluffyyarn.store.security.domain.model.role.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Aquí se hacen las validaciones de los campos que llegan del front.
@Data // Lombok - Genera getters, setters, toString, equals y hashCode.
@NoArgsConstructor // Lombok - Constructor vacío. Jackson lo necesita para deserializar el JSON que llega del front.
@AllArgsConstructor // Lombok - Constructor con todos los campos, lo usa el mapper.
public class UserDto {

  private Integer id; // lo devuelve la API, no lo manda el front

  @NotBlank(message = "El username es obligatorio")
  @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
  private String username;

  @NotBlank(message = "El password es obligatorio")
  @Size(min = 8, message = "El password debe tener al menos 8 caracteres")
  private String password;

  private Boolean isEnabled;

  @NotNull(message = "El rol es obligatorio")
  private RoleName roleName;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}