package com.fluffyyarn.store.identity.infrastructure.config;

import com.fluffyyarn.store.identity.application.port.out.role.RoleRepositoryPort;
import com.fluffyyarn.store.identity.application.port.out.user.UserRepositoryPort;
import com.fluffyyarn.store.identity.domain.exception.RoleNotFoundException;
import com.fluffyyarn.store.identity.domain.model.role.Role;
import com.fluffyyarn.store.identity.domain.model.role.RoleName;
import com.fluffyyarn.store.identity.domain.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Crea el usuario admin inicial con el que el cliente gestiona la tienda.
//
// Va en un CommandLineRunner y no en una migracion de Flyway porque Flyway no sabe
// hashear: su INSERT necesitaria un hash BCrypt ya calculado y quedaria hardcodeado
// en el repo. Aca se usa el mismo PasswordEncoder que despues usa el login, asi que
// el password llega en claro desde .env y sale hasheado a la base.
//
// Los roles los crea la migracion V2. Flyway corre antes que los ApplicationRunner,
// por eso RoleName.ADMIN ya existe cuando esto se ejecuta.
//
// Idempotente: si el admin ya esta en la base, no hace nada. Si el cliente le cambia
// la contrasena despues, el siguiente reinicio no la vuelve a pisar.
@Component
public class InitialAdminSeeder implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(InitialAdminSeeder.class);

  private final UserRepositoryPort userRepository;
  private final RoleRepositoryPort roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final String adminUsername;
  private final String adminPassword;

  public InitialAdminSeeder(
      UserRepositoryPort userRepository,
      RoleRepositoryPort roleRepository,
      PasswordEncoder passwordEncoder,
      @Value("${app.admin.username}") String adminUsername,
      @Value("${app.admin.password}") String adminPassword
  ) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.adminUsername = adminUsername;
    this.adminPassword = adminPassword;
  }

  @Override
  public void run(String... args) {
    // Sin default en application.properties: si falta ADMIN_PASSWORD, Spring no puede
    // resolver el placeholder y la app no arranca. El check de abajo cubre el caso de
    // que exista pero este vacia, que si resolveria.
    if (adminUsername == null || adminUsername.isBlank()) {
      throw new IllegalStateException(
          "ADMIN_USERNAME no esta definido. Ponelo en .env, ver .env.example.");
    }
    if (adminPassword == null || adminPassword.isBlank()) {
      throw new IllegalStateException(
          "ADMIN_PASSWORD no esta definido. Ponelo en .env, ver .env.example.");
    }

    if (userRepository.findByUsername(adminUsername).isPresent()) {
      // Ya existe: no se toca nada. Este es el caso normal en cada reinicio.
      log.info("Admin '{}' ya existe en la base, no se modifica.", adminUsername);
      return;
    }

    Role adminRole = roleRepository.findByName(RoleName.ADMIN)
        .orElseThrow(() -> new RoleNotFoundException(
            "Role with name " + RoleName.ADMIN + " not found."));

    userRepository.save(User.builder()
        .username(adminUsername)
        .password(passwordEncoder.encode(adminPassword))
        .isEnabled(true)
        .role(adminRole)
        .build());

    log.info("Admin inicial creado: '{}'. Cambia la contrasena tras el primer login.",
        adminUsername);
  }
}
