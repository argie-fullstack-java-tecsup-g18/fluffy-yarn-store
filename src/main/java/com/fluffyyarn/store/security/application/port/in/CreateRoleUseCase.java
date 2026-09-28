package com.fluffyyarn.store.security.application.port.in;

import com.fluffyyarn.store.security.domain.model.Role;

// Contrato que especifica los atributos que necesita un Role
public interface CreateRoleUseCase {
  Role create(CreateRoleCommand cmd);
}
