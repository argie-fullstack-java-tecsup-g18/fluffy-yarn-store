# Tareas — Ajustes post-migración a microservicios

## Fluffy Yarn Store

## 0. Primera prioridad: PostgreSQL + Redis para auth-service, MySQL para customer-service

### 0.1 auth-service — Cambiar MySQL por PostgreSQL ✅ (hecho, salvo docker-compose)

- [x] `pom.xml` de auth-service: quitar `com.mysql:mysql-connector-j`, agregar `org.postgresql:postgresql`
- [x] `pom.xml` de auth-service: quitar `flyway-mysql` (solo queda `flyway-core`; el driver de PostgreSQL basta)
- [x] `src/main/resources/application.properties`: cambiar datasource a PostgreSQL (
  `jdbc:postgresql://localhost:5432/auth_db`, driver `org.postgresql.Driver`), quitar parámetros MySQL (`useSSL`,
  `serverTimezone`, `allowPublicKeyRetrieval`)
    - Cambiar la URL del DataSource
      Sustituye tu línea actual de spring.datasource.url por esta:
      `spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/auth_db}`
    - Agregar el Driver de PostgreSQL: Agrega explícitamente la clase del driver justo debajo (o reemplaza el driver de
      MySQL si lo tenías declarado):
      `spring.datasource.driver-class-name=org.postgresql.Driver`
- RESULTADO:
    - `# Configuración de PostgreSQL para el Auth Service
    spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/auth_db}
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.datasource.username=${DB_USERNAME:postgres}
    spring.datasource.password=${DB_PASSWORD}`
    - `#Configuración de Hibernate / JPA para PostgreSQL
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true`
- [x] Crear carpeta `src/main/resources/db/migration/postgres/` con `V1__create_roles_and_users.sql`,
  `V2__seed_roles.sql`, `V3__seed_cliente1.sql` adaptados a PostgreSQL
- [x] Docker Compose: agregar servicio `postgres` (ya definido en el `docker-compose.yml` actual; falta verificar que
  arranque)

### 0.2 Redis para block list de tokens (logout real)

- [x] 1 `pom.xml` de auth-service: agregar `spring-boot-starter-data-redis`
- [x] 2 `pom.xml` de customer-service: agregar `spring-boot-starter-data-redis` (Opción A: respeta la block list también
  en customer-service)
  - [x] 3 `auth-service/src/main/resources/application.properties`: agregar `spring.data.redis.host=${REDIS_HOST:redis}` y
    `spring.data.redis.port=${REDIS_PORT:6379}`
      - [x] 4 `customer-service/src/main/resources/application.properties`: agregar
        `spring.data.redis.host=${REDIS_HOST:redis}` y `spring.data.redis.port=${REDIS_PORT:6379}`
          - [x] 5 `auth-service/.../security/TokenService.java`: agregar claim `jti` (`UUID.randomUUID().toString()`) a
            cada
            JWT
            generado
          <div style="background-color: #442856; border-radius: 4px;">

              ¿Qué es el jti?
              jti son las siglas de JWT ID (Identificador del JWT).

              Imagina que cada vez que generas un token para un usuario, le estás imprimiendo un número de serie único e irrepetible (
              un UUID aleatorio como f81d4fae-7dec-11d0-a765-00a0c91e6bf6). No hay dos tokens en todo el universo que vayan a tener el
              mismo jti.
            
              2. ¿Por qué hay que ponerlo? (La conexión con tu idea de Redis)
                 ¿Te acuerdas de que planeamos usar Redis para la lista negra de tokens cuando el usuario cierre sesión (Logout)? Aquí
                 es exactamente donde entra en juego el jti.
            
              Sin el jti: Si quisieras bloquear un token en Redis al hacer logout, tendrías que guardar el token completo (que es una
              cadena larguísima de texto cifrado) para decir "este token está prohibido". Eso consume más memoria y es menos elegante.
            
              Con el jti: Cuando el usuario hace logout, tu aplicación solo necesita guardar en Redis el identificador único (jti) de
              ese token con un tiempo de vida (TTL) igual al tiempo que le faltaba por expirar. Luego, cada vez que llegue una
              petición, tu filtro de seguridad lee el jti del token y le pregunta a Redis: "¿Este ID está en la lista negra?". Es
              ultrarrápido y consume poquísima memoria.
    
            UUID.randomUUID(): Genera un identificador universal único basado en números aleatorios de 128 bits.
            
            .toString(): Convierte ese identificador en un formato de texto estándar (una cadena alfanumérica con guiones).
            
            .jti(...): Le dice a la librería que genere el JWT insertando ese identificador único en el campo oficial jti del estándar de los tokens (RFC 7519).
            
            Al hacer esto, cada vez que un usuario inicie sesión y reciba su token, ese token vendrá "marcado" con su propio número de serie único, dándote el superpoder de revocarlo de forma individual en cualquier momento usando Redis. ¡Es una práctica de seguridad excelente!
          </div>

- [x] Crear `auth-service/.../security/TokenBlocklistService.java`: usar `StringRedisTemplate` con métodos
  `blocklist(jti, ttlSeconds)` y `isBlocklisted(jti)`; TTL = tiempo restante hasta el `exp` del token
- [x] Crear `auth-service/.../security/TokenBlocklistFilter.java` (`OncePerRequestFilter`): extraer `jti` del JWT
  autenticado; si está en Redis, devolver 401 con JSON `{timestamp, status, error, message}`
- [x] Registrar `TokenBlocklistFilter` en `auth-service/.../security/SecurityConfig.java` antes del filtro JWT de Spring
  Security
- [x] Crear endpoint `POST /auth/logout` en `auth-service/.../security/AuthController.java`: requiere autenticación (
  Bearer), extrae `jti` del token actual desde `SecurityContextHolder`, llama a
  `tokenBlocklistService.blocklist(jti, segundosRestantes)`, devuelve 200/204
- [x] `customer-service/.../security/SecurityConfig.java`: agregar el mismo `TokenBlocklistFilter` (o una copia
  adaptada) para que un token cerrado en auth-service también muera en customer-service

### 0.3 customer-service — Quedarse en MySQL

- [x] `customer-service/src/main/resources/application.properties`: mantener MySQL (
  `jdbc:mysql://mysql:3306/fluffy_yarn_db`, driver `com.mysql.cj.jdbc.Driver`); quitar propiedades `app.admin.*` (no
  corresponden a este dominio); mantener `spring.flyway.enabled=true` y `spring.flyway.locations=classpath:db/migration`
- [x] `customer-service/src/main/java/com/fluffyyarn/store/customer/domain/model/Customer.java`: quitar
  `import com.fluffyyarn.store.identity.domain.model.user.User`; reemplazar `private User user` por
  `private Integer userId` (romper acoplamiento con bounded context de auth)
- [ ] Crear `customer-service/src/main/resources/db/migration/V1__create_customer_profiles.sql`: tablas `customers` /
  `customer_profiles` en MySQL (columnas: id, user_id, name, lastname, document_number, email, address, ruc, created_at,
  updated_at; FK opcional a users de auth no se puede porque es otra DB, solo guardar user_id)
- [ ] Crear `customer-service/src/main/java/com/fluffyyarn/store/customer/infrastructure/security/SecurityConfig.java`:
  `oauth2ResourceServer` con `JwtDecoder` usando la misma `JWT_SECRET` que auth-service; matchers para `/customers/**`,
  `/customer-profiles/**`; `anyRequest().authenticated()`
- [ ] Implementar casos de uso de customer: `CreateCustomerProfileUseCase`, `GetCustomerProfileUseCase`,
  `UpdateCustomerProfileUseCase`, `ListCustomerProfilesUseCase` (interfaces en `application/port/in/`)
- [ ] Implementar servicios, repositorios JPA (`CustomerJpaRepository`, `CustomerPersistencyAdapter`,
  `CustomerPersistencyMapper`), controllers, mappers y DTOs
- [ ] Docker Compose: `customer-service` depende de `mysql` y `redis` (ya definido en el `docker-compose.yml` actual)

### 0.4 Docker Compose orquestado ✅ (definido)

- [x] Servicios: `mysql`, `postgres`, `redis`, `auth-service`, `customer-service`
- [x] Red Docker compartida para que los servicios se resuelvan por nombre de servicio
- [x] Variables de entorno en `.env` y `.env.example`: `REDIS_HOST`, `REDIS_PORT`, `JWT_SECRET` compartido,
  `ADMIN_PASSWORD`, `DB_PASSWORD`

---

## 1. Infraestructura y orquestación

> Los servicios `auth-service`, `customer-service`, `mysql`, `postgres`, `redis` y la red compartida ya están definidos
> en el **Paso 0.4**. Esta sección solo cubre lo que queda fuera de ese paso.

> **Nota sobre Spring Cloud:** Actualmente **no hay ninguna dependencia de Spring Cloud** en los proyectos
> (`auth-service/pom.xml` y `customer-service/pom.xml` no contienen `spring-cloud-starter-*` ni
> `spring-cloud-dependencies`). Para usar Spring Cloud Gateway como API Gateway es necesario:
>
> 1. Agregar el BOM de Spring Cloud en `<dependencyManagement>` de cada servicio (o del módulo gateway):
>    ```xml
>    <dependency>
>        <groupId>org.springframework.cloud</groupId>
>        <artifactId>spring-cloud-dependencies</artifactId>
>        <version>2023.0.0</version>
>        <type>pom</type>
>        <scope>import</scope>
>    </dependency>
>    ```
> 2. Agregar `spring-cloud-starter-gateway` al servicio que actuará como gateway (puede ser un módulo nuevo
>    `gateway-service` o uno existente).
> 3. Configurar las rutas en `application.properties`/`application.yml`, por ejemplo:
>    ```properties
>    spring.cloud.gateway.routes[0].id=auth-service
>    spring.cloud.gateway.routes[0].uri=http://auth-service:8080
>    spring.cloud.gateway.routes[0].predicates=Path=/auth/**
>    spring.cloud.gateway.routes[1].id=customer-service
>    spring.cloud.gateway.routes[1].uri=http://customer-service:8080
>    spring.cloud.gateway.routes[1].predicates=Path=/customers/**
>    ```

- [ ] **Dockerfile por servicio**: crear Dockerfile dedicado en auth-service y customer-service (actualmente solo existe
  uno en la raíz para el monolito) ✅ (creados en `auth-service/Dockerfile` y `customer-service/Dockerfile`)
- [ ] **API Gateway**: agregar Spring Cloud Gateway como punto de entrada único (ruteo `/auth/**` → auth-service,
  `/customers/**` → customer-service)
- [ ] **Service Discovery**: agregar Eureka/Consul o similar para que los servicios se localicen dinámicamente

## 2. Base de datos y datos (bounded contexts)

> Todo este bloque ya está cubierto en el **Paso 0** (auth-service → PostgreSQL, customer-service → MySQL, quitar
> acoplamiento a `identity.domain`, Flyway en customer-service, migraciones propias por servicio). No hay tareas
> pendientes adicionales aquí a menos que surjan nuevos bounded contexts.

## 3. Seguridad y autenticación

> `JWT_SECRET` compartido, validación de token en customer-service, blocklist/Redis y endpoint `/auth/logout` ya están
> en el **Paso 0.2**. Esta sección solo cubre lo que falta.

- [ ] **authenticationEntryPoint + accessDeniedHandler**: devolver JSON con formato
  `{timestamp, status, error, message}` en 401/403 (actualmente body vacío)
- [ ] **Rate limiting en `/auth/login`**: proteger contra fuerza bruta
- [ ] **Cambio de password propio del cliente**: endpoint `PATCH /customers/me/password` (pendiente desde el módulo
  CUSTOMER)

## 4. Comunicación entre servicios

- [ ] **Client HTTP inter-servicios**: agregar `spring-cloud-starter-openfeign` o `RestTemplate` para que
  customer-service consulte auth-service (ej. validar que el userId exista)
- [ ] **Eventos / mensajería**: evaluar RabbitMQ o Kafka para notificar eventos (userCreated, customerRegistered) en
  lugar de llamadas síncronas

## 5. Código y duplicación

> `SecurityConfig` en customer-service ya está definido en el **Paso 0.3**. Esta sección solo cubre lo que queda fuera
> de ese paso.

- [ ] **Librería compartida de seguridad**: extraer `SecurityConfig`, `JwtConfig`, `TokenService`,
  `GlobalExceptionHandler`, DTOs de error a un módulo común (`common` o `starter`) para no duplicar en cada servicio
- [ ] **Completar customer-service**: implementar casos de uso (`CreateCustomerProfileUseCase`, etc.), servicios,
  repositorios JPA, controllers, mappers y DTOs
- [ ] **Centralizar configuración**: evaluar Spring Cloud Config para propiedades compartidas (jwt.secret,
  spring.web.error, etc.)

## 6. Observabilidad

> Los `depends_on` con `condition: service_healthy` ya están en el **Paso 0.4**. Esta sección cubre lo que falta.

- [ ] **Spring Boot Actuator**: agregar endpoints `/health`, `/info` a cada servicio
- [ ] **Health checks explícitos en Docker Compose**: agregar `healthcheck` a cada servicio para dependencias de
  arranque
- [ ] **Logs estructurados**: evitar imprimir hashes BCrypt en show-sql (ya controlado con `application-dev.properties`)

## 7. Deuda técnica ya documentada (pendiente desde el monolito)

- [ ] **Tests**: agregar `UserServiceTest` y `RoleServiceTest` con `@Mock` sobre el puerto y `@InjectMocks`; configurar
  H2 en `src/test/resources/application.properties`
- [ ] **Paginación**: agregar `Pageable` a `GET /users` y `GET /roles`
- [ ] **Swagger / OpenAPI**: agregar anotaciones `@Tag` y `@Operation` a todos los controllers (dependencia ya está en
  el pom)
- [ ] **Decisión de seguridad en CUSTOMER**: definir si se usan matchers centralizados en `SecurityConfig` o
  `@PreAuthorize` por método (opción A, B o C documentada en `pendientes.txt`)

## 8. Documentación

> Las variables nuevas para `.env` y `.env.example` ya están anotadas en el **Paso 0.4**. Esta sección solo cubre lo que
> falta.

- [ ] **README**: actualizar instrucciones de arranque para microservicios (docker compose con varios servicios, puertos
  por servicio)
- [ ] **endpoints.md**: documentar rutas de customer-service y cambios de base URL por servicio
