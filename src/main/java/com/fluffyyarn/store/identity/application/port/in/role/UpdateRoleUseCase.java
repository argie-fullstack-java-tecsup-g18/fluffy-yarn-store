package com.fluffyyarn.store.identity.application.port.in.role;

import com.fluffyyarn.store.identity.domain.model.role.Role;

public interface UpdateRoleUseCase {
  Role update(UpdateRoleCommand cmd);
}
