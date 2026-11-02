package com.fluffyyarn.store.identity.infrastructure.adapter.in.role;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// POR AQUI PASA EL REQUEST Y SE VALIDA Permite aplicar reglas o anotaciones
// (por ejemplo, verificar que un correo sea válido, que un campo no esté vacío o que un número esté en un rango) antes de que los datos lleguen a la lógica de negocio

// Aquí se hacen las validaciones de los campos que llegan del front.
@Data // Lombok - Genera getters, setters, toString, equals y hashCode.
@NoArgsConstructor // Lombok - Constructor vacío. Jackson lo necesita para deserializar el JSON que llega del front.
@AllArgsConstructor // Lombok - Constructor con todos los campos, lo usa el mapper.
public class RoleDto {

  private Short id; // lo devuelve la API, no lo manda el front

  @NotNull(message = "El nombre del rol es obligatorio")
  private RoleName name;

  private String description;

  private LocalDateTime createdAt;
}