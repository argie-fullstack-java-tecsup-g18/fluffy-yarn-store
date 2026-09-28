package com.fluffyyarn.store.security.domain.model.user;

import com.fluffyyarn.store.security.domain.model.role.Role;

import java.time.LocalDateTime;

public class User {
  private Integer id; // TODO: Preguntar: por qué el profe usa LONG?
  private String username;
  private String password;
  private Boolean isEnabled;
  private Role role; // Para la FK
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
