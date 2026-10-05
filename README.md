# Fluffy Yarn Store

API REST de una tienda, construida con **Spring Boot** y **MySQL 8** orquestado con **Docker Compose**.

## Stack

| Tecnología | Versión |
|---|---|
| Java | 25 |
| Spring Boot | 4.1.1 |
| Spring Data JPA | (incluida) |
| springdoc-openapi (Swagger) | 3.1.0 |
| MySQL | 8.0 |
| Docker Compose | 3.8 |

## Prerrequisitos

- **JDK 25** instalado
- **Docker** y **Docker Compose** (v2+)

## Setup

```bash
# 1. Clona el repositorio
git clone <url-del-repo>
cd store

# 2. Crea tu archivo de credenciales
cp .env.example .env

# 3. Edita .env y pon tu contraseña en DB_PASSWORD

# 4. Levanta solo la base de datos
docker compose up -d mysql

# 5. Arranca la aplicación
./mvnw spring-boot:run
```

La API queda en http://localhost:8080 y la documentación interactiva en
**http://localhost:8080/swagger-ui.html**

> No necesitas hacer `source .env` en el terminal: `application.properties` importa
> el archivo `.env` automáticamente en la línea `spring.config.import`.

### Ejecutar todo en Docker

```bash
docker compose up --build
```

## Puertos

| Servicio | Puerto |
|---|---|
| Aplicación | 8080 |
| MySQL | 3306 |

## Variables de entorno

Todas se definen en `.env` (ignorado por git). Copia `.env.example` para empezar.

| Variable | Requerida | Default | Descripción |
|---|---|---|---|
| `DB_PASSWORD` | **sí** | — | Contraseña de MySQL. Sin default: si falta, el arranque falla a propósito |
| `DB_USERNAME` | no | `root` | Usuario de MySQL |
| `MYSQL_DATABASE` | no | `fluffy_yarn_db` | Nombre de la base de datos |
| `DB_URL` | no | `localhost:3306` | JDBC URL. Docker Compose lo sobrescribe con el host `mysql` |

`DB_PASSWORD` alimenta **tanto** MySQL como la app Spring Boot. Al ser una única
fuente, la aplicación nunca puede fallar al conectar por contraseñas distintas.

## Comandos útiles

```bash
./mvnw test                     # Correr los tests
./mvnw spring-boot:run                    # Arrancar en local
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev   # Con show-sql activo
./mvnw clean package            # Empaquetar el JAR en target/

podman compose up -d mysql      # Solo la base de datos
podman compose up --build       # Todo el stack
podman compose logs -f app      # Ver logs de la app
podman compose down             # Detener (conserva el volumen de datos)
podman compose down -v          # Detener y BORRAR la base de datos
```

En esta máquina los contenedores corren con **podman**, no con docker. Si tenés docker
instalado, `docker compose` funciona igual sobre el mismo `docker-compose.yml`.

> `down -v` borra el volumen y con él todas las migraciones aplicadas. Al arrancar de
> nuevo, Flyway vuelve a correr `V1`, `V2` y `V3` desde cero y `InitialAdminSeeder`
> recrea el admin: es la forma limpia de reconstruir la base.

## Estructura del proyecto

```
store/
├── src/main/java/com/fluffyyarn/store/
│   └── StoreApplication.java      # Punto de entrada
├── src/main/resources/
│   ├── application.properties     # Configuración
│   ├── application-dev.properties # Perfil dev (show-sql)
│   ├── db/migration/              # Flyway: V1 esquema, V2 roles, V3 cliente1
│   ├── static/                    # Recursos estáticos
│   └── templates/                 # Vistas HTML
├── src/test/java/                 # Tests
├── docker-compose.yml             # MySQL + app
├── Dockerfile                     # Build multi-stage
├── .env.example                   # Plantilla de credenciales (sube a git)
└── .env                           # Tus credenciales (NO sube a git)
```

## Control de acceso

Definido en `security/SecurityConfig.java`. Todo lo que no figura cae en
`anyRequest().authenticated()`, es decir, necesita token válido pero no un rol en
particular.

| Método | Ruta | Quién |
|---|---|---|
| POST | `/users` (registro) | público |
| POST | `/auth/login` | público |
| GET | `/swagger-ui/**`, `/v3/api-docs/**` | público |
| GET | `/users`, `/roles` y sus variantes | **ADMIN** |
| POST | `/roles` | **ADMIN** |
| POST | `/users/create` | **ADMIN** |
| PATCH | `/users/password` | **ADMIN** |
| PATCH | `/users/status` | **ADMIN** |
| PATCH | `/roles/{id}` | **ADMIN** |
| PATCH | `/users/{id}` | **ADMIN** |

> `PATCH /users/password` y `PATCH /users/status` son acciones de administración: sin
> esas dos reglas, cualquier cliente autenticado podía mandar el username de otro
> usuario junto con una password nueva y apoderarse de su cuenta. El cambio de
> password propio del cliente está pendiente de diseñar cuando exista el módulo
> `CUSTOMER`.
>
> `PATCH /roles/{id}` y `PATCH /users/{id}` actualizan la descripción de un rol y el
> `username`/`role` de un usuario. Van sobre la path (`/users/1`) y no sobre el body,
> porque el id identifica al recurso que se modifica.

## Pendientes / Debt técnico

Cosas detectadas durante la preparación del repositorio, pendientes a propósito:

- [ ] **Los tests fallarán al correr `./mvnw test`.** `StoreApplicationTests` intenta
      levantar el contexto de Spring y necesita MySQL, pero no hay configuración ni
      base de datos de pruebas. Hay que decidir entre **H2** (rápido, tests aislados)
      o **Testcontainers** (MySQL real, más fiel pero más lento).
- [ ] **Falta `spring-boot-starter-thymeleaf` en el `pom.xml`.** Las carpetas
      `templates/` y `static/` existen, pero sin Thymeleaf las vistas HTML no se van
      a renderizar. Hay que agregar la dependencia o eliminar esas carpetas.
- [x] **`spring.jpa.show-sql=true` estaba activo.** Resuelto: ahora vive en
      `application-dev.properties` y el `application.properties` base lo tiene en
      `false`. Se activa con `--spring.profiles.active=dev`. Motivo: cada INSERT de
      usuario imprimía el hash BCrypt completo en el log.
- [ ] `HELP.md` se ignora en `.gitignore`; su información ya está en este README.

### Deuda técnica acumulada

Detectados al implementar el módulo `security` (pasos 4-11 de la guía de Notion):

- [x] **La tabla se llama `roles`, no `role`.** El `@Table(name = "roles")` de
      `RoleEntity` no coincide con el `Table role` del DBML original. Definir
      cuál es el nombre oficial. Resuelto: `roles`.
- [x] **Endpoints de `Role` sin autenticación.** Resuelto: `POST /roles` quedó
      restringido a `ADMIN` en `SecurityConfig`, igual que el alta de usuarios y la
      mutación de password/estado.
- [x] **La tabla `roles` necesita seed.** Resuelto con Flyway en `db/migration/`:
      `V1` crea `roles` y `users` con DDL portable, `V2` siembra los 4 roles
      (`ADMIN`, `CUSTOMER`, `SELLER`, `WAREHOUSE`) y `V3` siembra el usuario demo
      `cliente1` (`cliente123`, rol CUSTOMER), que es el que recibe `403` en los
      endpoints reservados a `ADMIN`. El admin no va en una migración: lo crea
      `InitialAdminSeeder` a partir de `ADMIN_PASSWORD`, porque Flyway no sabe
      hashear y dejar el hash en el repo sería meter la credencial en git.
- [x] **`UpdateRoleUseCase`.** Resuelto: `PATCH /roles/{id}` solo acepta `description`
      (`name` es el enum de los 4 roles y no tiene sentido editarlo). Reservado a
      `ADMIN`.
- [x] **`UpdateUserUseCase`.** Resuelto: `PATCH /users/{id}` actualiza `username` y
      `roleName`, ambos opcionales. Reservado a `ADMIN`. Ojo: el token vigente del
      usuario renombrado sigue vivo hasta los 60 min, porque el JWT lleva el nombre
      embebido y nadie lo vuelve a leer.
