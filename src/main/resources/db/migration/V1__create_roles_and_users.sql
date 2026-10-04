-- Esquema base de identity: roles y users.
--
-- DDL literal de SHOW CREATE TABLE sobre fluffy_yarn_db. Se copia tal cual porque
-- ddl-auto=validate compara tipos de columna contra lo que Hibernate espera, y
-- Hibernate genero name como enum, no como varchar. Si aca se escribiera
-- varchar(255) leyendose mas simple, la app no arrancaria.
--
-- roles va primero: users tiene FK hacia roles(id).
CREATE TABLE `roles` (
    `id`          smallint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) NOT NULL,
    `description` varchar(255) DEFAULT NULL,
    `name`        enum('ADMIN','CUSTOMER','SELLER','WAREHOUSE') NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
    `id`         int NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6) NOT NULL,
    `is_enabled` bit(1) NOT NULL,
    `password`   varchar(255) DEFAULT NULL,
    `updated_at` datetime(6) NOT NULL,
    `username`   varchar(255) NOT NULL,
    `role_id`    smallint DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
    KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
    CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
