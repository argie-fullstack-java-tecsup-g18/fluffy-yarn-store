package com.fluffyyarn.store.identity.application.port.in.role;

import com.fluffyyarn.store.identity.domain.model.role.Role;

// Contrato que especifica los atributos que necesita un Role
public interface CreateRoleUseCase {
  Role create(CreateRoleCommand cmd);
}
