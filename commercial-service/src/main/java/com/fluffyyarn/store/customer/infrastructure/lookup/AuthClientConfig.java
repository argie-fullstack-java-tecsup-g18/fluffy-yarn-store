package com.fluffyyarn.store.customer.infrastructure.lookup;

import com.fluffyyarn.store.customer.domain.exception.ExternalServiceException;
import com.fluffyyarn.store.customer.domain.exception.StaleRoleException;
import feign.RequestInterceptor;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


// Deliberately NOT annotated with @Configuration: it applies only to AuthClient (Feign creates a child context for it).
class AuthClientConfig {

  // The call is made on behalf of the user: the caller's token travels with it. A service-to-service credential
  // would be the alternative (not implemented). X-Request-Id keeps the correlation id across the hop.
  @Bean
  RequestInterceptor forwardCallerHeaders() {
    return template -> {
      if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
        for (String header : new String[]{HttpHeaders.AUTHORIZATION, "X-Request-Id"}) {
          String value = attributes.getRequest().getHeader(header);
          if (value != null) {
            template.header(header, value);
          }
        }
      }
    };
  }

  // Does not read the body of the error: it may not be JSON (a proxy page, an empty 502...).
  // Fail-closed: 404 (el usuario del token ya no existe) y 403 (auth-service negó
  // el token) son roles/estado stale → StaleRoleException → 403. Cualquier otro
  // código inesperado (401 mid-hop, 5xx, red) → ExternalServiceException → 503:
  // la operación destructiva no se ejecuta ante la duda.
  @Bean
  ErrorDecoder authErrorDecoder() {
    return (String methodKey, Response response) -> switch (response.status()) {
      case 404, 403 -> new StaleRoleException(
          "auth-service no puede confirmar tu rol con este token. Vuelve a iniciar sesión.");
      default -> new ExternalServiceException("auth-service answered " + response.status());
    };
  }
}
