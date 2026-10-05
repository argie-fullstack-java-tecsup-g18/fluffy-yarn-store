package com.fluffyyarn.store.identity.infrastructure.security;

import com.fluffyyarn.store.identity.application.port.out.user.UserRepositoryPort;
import com.fluffyyarn.store.identity.domain.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Reemplaza al InMemoryUserDetailsManager del profe: en vez de un usuario fijo
// escrito en el codigo, busca en la tabla users. Con esto el login funciona con
// los usuarios reales y desaparece el password "1234" hardcodeado.
@Service
public class CustomUserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserRepositoryPort repository;

  public CustomUserDetailsService(UserRepositoryPort repository) {
    this.repository = repository;
  }

  // Spring llama a este metodo con el username que manda el cliente. Si
  // devolvemos null o lanzamos UsernameNotFoundException, el
  // DaoAuthenticationProvider responde 401 con credenciales invalidas.
  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = this.repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "User with username " + username + " not found."));

    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        authorities(user));
  }

  private List<GrantedAuthority> authorities(User user) {
    return List.of(
        new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().name())
    );
  }
}