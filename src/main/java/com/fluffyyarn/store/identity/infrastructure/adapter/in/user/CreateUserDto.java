package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Alta de un usuario hecha por un admin.
// A diferencia del registro público, acá el admin sí elige el rol.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

  @NotBlank(message = "El username es obligatorio")
  @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
  private String username;

  @NotBlank(message = "El password es obligatorio")
  @Size(min = 8, message = "El password debe tener al menos 8 caracteres")
  private String password;

  @NotNull(message = "El rol es obligatorio")
  private RoleName roleName;
}
