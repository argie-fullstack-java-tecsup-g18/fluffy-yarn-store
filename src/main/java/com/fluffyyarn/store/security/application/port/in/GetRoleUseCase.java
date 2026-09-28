package com.fluffyyarn.store.security.application.port.in;

import com.fluffyyarn.store.security.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.security.domain.model.Role;
import com.fluffyyarn.store.security.domain.model.RoleName;

import java.util.List;

// (TODO: revisar solo comentario) Contrato que especifica los parametros que necesita un Role para devolverlos según el método llamado
public interface GetRoleUseCase {
  Role findById(Long roleId) throws RoleNotFoundException;

  Role findByName(RoleName roleName);

  List<Role> findAll();
}
