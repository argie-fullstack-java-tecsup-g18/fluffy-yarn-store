package com.fluffyyarn.store.identity.infrastructure.adapter.in.user;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// PATCH de un usuario. Se identifica por el id de la path, no por el body: si el
// username se puede cambiar, el del body es ambiguo porque no sabes si manda el
// viejo o el nuevo.
//
// Los dos campos son opcionales, igual que en UpdateRoleDto. El password no aparece:
// el cambio de password es un endpoint aparte (PATCH /users/password) con su propia
// validacion de longitud.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {

  @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
  private String username;

  private RoleName roleName;
}
