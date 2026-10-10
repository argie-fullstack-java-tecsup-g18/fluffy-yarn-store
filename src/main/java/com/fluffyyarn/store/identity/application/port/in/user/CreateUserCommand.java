package com.fluffyyarn.store.identity.application.port.in.user;

import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

// Para el ADMIN
@Getter
@AllArgsConstructor
@Builder
public class CreateUserCommand implements UserCommand {
  private String username;
  private String password;
  private RoleName roleName;
}
