-- El hash lo genero BCryptPasswordEncoder con la misma configuracion que usa la app
-- (SecurityConfig.passwordEncoder(), strength 10, $2a$)
INSERT INTO users (username, password, is_enabled, role_id, created_at, updated_at)
SELECT 'cliente1',
       '$2a$10$BceKp9AbWVK.yaP0Z/DFSuJO4hqMt8CIYKMU6Hsz/TgE9jveKu8SO',
       TRUE,
       r.id,
       CURRENT_TIMESTAMP,
       CURRENT_TIMESTAMP
FROM roles r
WHERE r.name = 'CUSTOMER';
