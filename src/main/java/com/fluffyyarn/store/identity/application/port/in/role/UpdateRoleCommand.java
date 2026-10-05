package com.fluffyyarn.store.identity.application.port.in.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UpdateRoleCommand {
  private Short id;
  private String description;
}
