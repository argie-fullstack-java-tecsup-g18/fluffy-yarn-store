-- Roles base del sistema.
--
-- Idempotente por el unique en roles.name: si esta migracion corriera dos veces,
-- el INSERT simple fallaria. Con INSERT IGNORE las repetidas no hacen nada.
-- created_at es NOT NULL, por eso se trae CURRENT_TIMESTAMP.
--
-- Estos cuatro los usa el registro publico (CUSTOMER) y el InitialAdminSeeder (ADMIN),
-- asi que tienen que existir antes de que arranque cualquier ApplicationRunner.
-- Flyway corre antes que los runners, asi que el orden sale solo.
INSERT IGNORE INTO roles (name, description, created_at) VALUES
    ('ADMIN',     'Administrador total del sistema / dueño',     CURRENT_TIMESTAMP),
    ('CUSTOMER',  'Cliente final',                              CURRENT_TIMESTAMP),
    ('SELLER',    'Vendedor / Cajero (tienda física o POS)',     CURRENT_TIMESTAMP),
    ('WAREHOUSE', 'Almacenero / Encargado de despachos e inventarios', CURRENT_TIMESTAMP);
