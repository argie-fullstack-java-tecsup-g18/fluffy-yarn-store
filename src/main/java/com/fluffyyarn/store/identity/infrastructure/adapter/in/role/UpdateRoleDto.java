package com.fluffyyarn.store.identity.infrastructure.adapter.in.role;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// PATCH de un rol. Solo se puede cambiar la description: name es el enum RoleName,
// con cuatro valores fijos, y renombrarlo seria convertir un rol en otro.
//
// Los dos campos del contrato del PATCH son opcionales: lo que venga en null no se
// toca. Un body vacio devuelve 200 con el rol igual que estaba.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleDto {

  @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
  private String description;
}
