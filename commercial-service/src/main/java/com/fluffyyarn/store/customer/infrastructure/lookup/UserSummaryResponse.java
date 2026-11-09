package com.fluffyyarn.store.customer.infrastructure.lookup;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The contract of GET /users/username/{username}, owned by auth-service. This is this service's own copy: nothing is imported from the other project.
 * Los nombres son el JSON real que emite auth-service (UserResponseDto): el
 * boolean viaja como "isEnabled" (@JsonProperty sobre su atributo "enabled")
 * y el rol como "roleName" (enum).
 */
record UserSummaryResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("username") String username,
    @JsonProperty("isEnabled") Boolean isEnabled,
    @JsonProperty("roleName") String roleName
) {
}
