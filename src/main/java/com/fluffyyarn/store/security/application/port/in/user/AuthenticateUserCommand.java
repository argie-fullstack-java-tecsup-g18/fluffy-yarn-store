package com.fluffyyarn.store.security.application.port.in.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthenticateUserCommand {
  private String username;
  private String password;
}