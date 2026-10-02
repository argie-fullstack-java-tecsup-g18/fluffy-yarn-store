package com.fluffyyarn.store.identity.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // El registro es público: cualquiera se da de alta y sale como CUSTOMER.
            // Solo POST; el resto de /users (create, authenticate, listar) sigue protegido.
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults());

    return http.build();
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
}