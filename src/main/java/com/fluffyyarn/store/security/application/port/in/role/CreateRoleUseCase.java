package com.fluffyyarn.store.security.application.port.in.role;

import com.fluffyyarn.store.security.domain.model.role.Role;

// Contrato que especifica los atributos que necesita un Role
public interface CreateRoleUseCase {
  Role create(CreateRoleCommand cmd);
}
