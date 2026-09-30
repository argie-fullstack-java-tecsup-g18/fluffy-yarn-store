package com.fluffyyarn.store.security.application.port.in.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter // Lombok - Genera los getters de todos los atributos.
@AllArgsConstructor // Lombok - Genera el constructor con todos los atributos.
@Builder // Lombok -
public class ToggleUserStatusCommand {
  private String username;
  private boolean isEnabled;
}
