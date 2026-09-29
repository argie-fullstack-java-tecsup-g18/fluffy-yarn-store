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
./mvnw spring-boot:run          # Arrancar en local
./mvnw clean package            # Empaquetar el JAR en target/

docker compose up -d mysql      # Solo la base de datos
docker compose up --build       # Todo el stack
docker compose logs -f app      # Ver logs de la app
docker compose down             # Detener (conserva el volumen de datos)
docker compose down -v          # Detener y BORRAR la base de datos
```

## Estructura del proyecto

```
store/
├── src/main/java/com/fluffyyarn/store/
│   └── StoreApplication.java      # Punto de entrada
├── src/main/resources/
│   ├── application.properties     # Configuración
│   ├── static/                    # Recursos estáticos
│   └── templates/                 # Vistas HTML
├── src/test/java/                 # Tests
├── docker-compose.yml             # MySQL + app
├── Dockerfile                     # Build multi-stage
├── .env.example                   # Plantilla de credenciales (sube a git)
└── .env                           # Tus credenciales (NO sube a git)
```

## Pendientes / Debt técnico

Cosas detectadas durante la preparación del repositorio, pendientes a propósito:

- [ ] **Los tests fallarán al correr `./mvnw test`.** `StoreApplicationTests` intenta
      levantar el contexto de Spring y necesita MySQL, pero no hay configuración ni
      base de datos de pruebas. Hay que decidir entre **H2** (rápido, tests aislados)
      o **Testcontainers** (MySQL real, más fiel pero más lento).
- [ ] **Falta `spring-boot-starter-thymeleaf` en el `pom.xml`.** Las carpetas
      `templates/` y `static/` existen, pero sin Thymeleaf las vistas HTML no se van
      a renderizar. Hay que agregar la dependencia o eliminar esas carpetas.
- [ ] **`spring.jpa.show-sql=true` está activo.** Ruidoso para producción. Cuando se
      implemente la separación de perfiles, moverlo a un perfil `dev`.
- [ ] `HELP.md` se ignora en `.gitignore`; su información ya está en este README.

### Deuda técnica acumulada

Detectados al implementar el módulo `security` (pasos 4-11 de la guía de Notion):

- [x] **La tabla se llama `roles`, no `role`.** El `@Table(name = "roles")` de
      `RoleEntity` no coincide con el `Table role` del DBML original. Definir
      cuál es el nombre oficial. Resuelto: `roles`.
- [ ] **Endpoints de `Role` sin autenticación.** `POST /roles` es un endpoint de
      administración expuesto. Al agregar Spring Security debe quedar restringido
      a `ADMIN`.
- [ ] **La tabla `roles` necesita seed.** `ddl-auto=update` crea la tabla vacía,
      pero los 4 roles (`ADMIN`, `CUSTOMER`, `SELLER`, `WAREHOUSE`) deben existir
      como datos. Con Flyway será una migración.
- [ ] **Falta `UpdateRoleUseCase`.** Pendiente según la guía de Notion del profe
      (paso 5.1): modifica la descripción o el nombre de un rol existente.
- [ ] **Falta `UpdateUserUseCase`.** Análogo al `UpdateRoleUseCase`: actualiza el
      `username` o el `role` de un usuario existente.
