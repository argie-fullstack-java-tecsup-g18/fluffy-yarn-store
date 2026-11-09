package com.fluffyyarn.store.customer.infrastructure.lookup;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Declarative HTTP client. It lives in infrastructure, behind the UserVerificationPort (AuthLiveAdminVerifier): the domain never hears about Feign.
// No service discovery: the URL comes from the configuration (config-repo).
@FeignClient(
    name = "auth-service",
    url = "${store.auth-service.url}",
    configuration = AuthClientConfig.class
)
interface AuthClient {

  @GetMapping("/users/username/{username}")
  UserSummaryResponse findByUsername(@PathVariable("username") String username);
}
