package com.fluffyyarn.store.security.application.port.in.user;

import com.fluffyyarn.store.security.domain.model.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// Para el ROLE_ADMIN
@Getter
@AllArgsConstructor
@Builder
public class CreateUserCommand {
  private String username;
  private String password;
  private RoleName roleName;
}
