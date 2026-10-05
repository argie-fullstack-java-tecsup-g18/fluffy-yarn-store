package com.fluffyyarn.store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  // Cadena de filtros de seguridad (son secuenciales o en cascada como css)
  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtAuthenticationConverter jwtAuthenticationConverter
  ) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(
                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                "/webjars/swagger-ui/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/roles").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/users/create").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/users/password").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/users/status").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/roles/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PATCH, "/users/{id}").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/roles/**").hasRole("ADMIN")
            .anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    // Ensambla y retorna el Bean SecurityFilterChain procesado para registrar la seguridad global en la aplicación.
    return http.build();
  }

  // Le indica a Spring Security que busque la lista de roles del usuario en la propiedad/campo "roles" dentro del JSON del token JWT
  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
    authorities.setAuthoritiesClaimName("roles");
    authorities.setAuthorityPrefix("ROLE_");

    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(authorities);
    return converter;
  }

  // Para codificar las contrasenas y retornar un Bcript
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Crea el Bean de AuthenticationManager que procesará los logins.
   * Usa un DaoAuthenticationProvider que:
   * 1. Consulta al UserDetailsService para obtener los datos del usuario en BD.
   * 2. Compara la contraseña mediante el PasswordEncoder.
   */
  @Bean
  AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder
  ) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return new ProviderManager(provider);
  }
}
