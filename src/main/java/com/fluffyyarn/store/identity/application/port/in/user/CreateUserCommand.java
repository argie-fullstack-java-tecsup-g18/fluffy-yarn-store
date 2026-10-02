package com.fluffyyarn.store.identity.application.port.in.user;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// Para el ADMIN
@Getter
@AllArgsConstructor
@Builder
public class CreateUserCommand {
  private String username;
  private String password;
  private RoleName roleName;
}
