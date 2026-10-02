package com.fluffyyarn.store.identity.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  // Cadena de filtros de seguridad (son secuenciales o en cascada como css)
  @Bean
  SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      JwtAuthenticationConverter jwtAuthenticationConverter
  ) throws Exception {
    http
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/authenticate").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/roles").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST, "/users/create").hasRole("ADMIN")
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

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder encoder) {
    return new InMemoryUserDetailsManager(
        User.builder().username("user")
            .password(encoder.encode("1234"))
            .roles("ADMIN").build());
  }

  // Crea un proveedor de autenticación que consulta a la base de datos (u otra fuente) mediante el UserDetailsService para buscar los datos del usuario por su nombre de usuario.
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