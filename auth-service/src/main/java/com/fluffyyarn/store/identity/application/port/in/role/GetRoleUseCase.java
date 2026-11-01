package com.fluffyyarn.store.identity.application.port.in.role;

import com.fluffyyarn.store.identity.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.identity.domain.model.role.Role;
import com.fluffyyarn.store.identity.domain.model.role.RoleName;

import java.util.List;

// En los casos de uso se declaran qué operaciones existen antes de decidir cómo se hacen.
// Sin esto, el controller no sabría a qué llamar.
// (TODO: revisar solo comentario) Contrato que especifica los parametros que necesita un Role para devolverlos según el método llamado
public interface GetRoleUseCase {
  Role findById(Short roleId) throws RoleNotFoundException;

  Role findByName(RoleName roleName);

  List<Role> findAll();
}
