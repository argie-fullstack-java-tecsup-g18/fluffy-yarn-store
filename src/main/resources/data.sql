-- Seed de los roles base del sistema.
-- El unique en roles.name (ver RoleEntity) hace que este INSERT sea idempotente:
-- la primera vez inserta, en los siguientes arranques INSERT IGNORE no hace nada.
-- created_at es NOT NULL en el esquema, por eso se trae CURRENT_TIMESTAMP.
INSERT IGNORE INTO roles (name, description, created_at) VALUES
  ('ADMIN',     'Administrador total del sistema / dueño',                    CURRENT_TIMESTAMP),
  ('CUSTOMER',  'Cliente final',                                             CURRENT_TIMESTAMP),
  ('SELLER',    'Vendedor / Cajero (tienda física o POS)',                   CURRENT_TIMESTAMP),
  ('WAREHOUSE', 'Almacenero / Encargado de despachos e inventarios',         CURRENT_TIMESTAMP);
