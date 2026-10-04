-- Roles base del sistema.
INSERT IGNORE INTO roles (name, description, created_at) VALUES
    ('ADMIN',     'Administrador total del sistema / dueño',     CURRENT_TIMESTAMP),
    ('CUSTOMER',  'Cliente final',                              CURRENT_TIMESTAMP),
    ('SELLER',    'Vendedor / Cajero (tienda física o POS)',     CURRENT_TIMESTAMP),
    ('WAREHOUSE', 'Almacenero / Encargado de despachos e inventarios', CURRENT_TIMESTAMP);
