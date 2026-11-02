package com.fluffyyarn.store.security.infrastructure.adapter.in.filter;

import com.fluffyyarn.store.security.application.port.in.TokenBlocklistPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

  // OncePerRequestFilter - clase especial de Spring que intercepta todas y cada una de las peticiones HTTP que llegan al servidor, ejecuta el metodo central doFilterInternal.
public class TokenBlocklistFilter extends OncePerRequestFilter {

  private final TokenBlocklistPort blocklistPort;

  public TokenBlocklistFilter(TokenBlocklistPort blocklistPort) {
    this.blocklistPort = blocklistPort;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    // contexto de seguridad de Spring para ver si hay un token válido y si el usuario ya pasó la autenticación inicial.
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof Jwt jwt) {
      String jti = jwt.getClaimAsString("jti");
      if (jti != null && blocklistPort.isBlocklisted(jti)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        Map<String, Object> body = Map.of(
            "timestamp", Instant.now().toString(),
            "status", 401,
            "error", "Unauthorized",
            "message", "Token revocado. Inicia sesión nuevamente."
        );
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        return;
      }
    }

    //Si el token no está en la lista negra (o si la petición es pública y no lleva token), el filtro le da luz verde (filterChain.doFilter) para que la petición continúe su camino normal hacia el controlador que corresponda.
    filterChain.doFilter(request, response);
  }
}