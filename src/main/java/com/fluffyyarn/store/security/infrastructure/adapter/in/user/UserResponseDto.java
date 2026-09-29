package com.fluffyyarn.store.security.infrastructure.adapter.in.user;

import com.fluffyyarn.store.security.domain.model.role.RoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// Omite el password porque el web mapper no requiere el password
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

  private Integer id;
  private String username;
  private Boolean isEnabled;
  private RoleName roleName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
