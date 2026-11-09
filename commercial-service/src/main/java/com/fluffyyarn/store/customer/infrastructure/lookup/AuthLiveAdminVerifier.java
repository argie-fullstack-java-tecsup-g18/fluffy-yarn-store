package com.fluffyyarn.store.customer.infrastructure.lookup;

import com.fluffyyarn.store.customer.application.port.out.user.UserVerificationPort;
import com.fluffyyarn.store.customer.domain.exception.StaleRoleException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

// Adaptador OUT de UserVerificationPort. Sobre el token YA validado por el
// JwtDecoder de este servicio toma el "sub" (username) y pregunta a auth-service
// con él (el token del llamador viaja por el interceptor de AuthClientConfig):
// la respuesta trae SU fila con el rol vigente de la BD, no el del token. Si el
// endpoint de auth-service no responde como admin espera (403/404/5xx), el
// ErrorDecoder ya eleva StaleRoleException o ExternalServiceException.
@Component
class AuthLiveAdminVerifier implements UserVerificationPort {

  private final AuthClient authClient;

  AuthLiveAdminVerifier(AuthClient authClient) {
    this.authClient = authClient;
  }

  @Override
  public void assertCallerIsLiveAdmin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
      throw new StaleRoleException("No hay usuario autenticado para verificar.");
    }

    String username = jwt.getSubject();
    if (username == null || username.isBlank()) {
      throw new StaleRoleException("Tu token no identifica al usuario. Vuelve a iniciar sesión.");
    }

    UserSummaryResponse live = this.authClient.findByUsername(username);

    // Fail-closed: un campo ausente (JSON desalineado) cuenta como denegado.
    if (!Boolean.TRUE.equals(live.isEnabled())) {
      throw new StaleRoleException(
          "Tu cuenta está deshabilitada en el sistema de usuarios. Inicia sesión de nuevo con una cuenta activa.");
    }
    if (!"ADMIN".equals(live.roleName())) {
      throw new StaleRoleException(
          "Tu rol vigente es " + live.roleName()
              + " y esta operación exige ADMIN: tu sesión trae roles desactualizados. "
              + "Inicia sesión de nuevo.");
    }
  }
}
