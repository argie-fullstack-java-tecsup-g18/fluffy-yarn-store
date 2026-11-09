package com.fluffyyarn.store.customer.application.port.out.user;

/**
 * Segunda línea de defensa contra tokens vigentes con datos desactualizados:
 * la primera es SecurityConfig/@PreAuthorize (validan el claim "roles" del
 * token, congelado al momento del login). Esta verificación pregunta a
 * auth-service por el rol y el estado VIGENTES en la BD. La implementación
 * vive en infraestructura/lookup detrás de Feign: el dominio nunca oye de HTTP.
 */
public interface UserVerificationPort {

  /**
   * Verifica que quien hace la petición en curso siga siendo ADMIN habilitado
   * al momento de la consulta. Lanza StaleRoleException si no (o si no hay
   * forma de saberlo): fail-closed.
   */
  void assertCallerIsLiveAdmin();
}
