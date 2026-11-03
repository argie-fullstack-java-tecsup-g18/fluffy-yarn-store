-- Alinea el esquema con UserEntity (Long id => BIGINT).
-- La V1 creo users.id como INT, lo que hace fallar spring.jpa.hibernate.ddl-auto=validate
-- con: "wrong column type encountered in column [id] in table [users];
-- found [int4], but expecting [bigint]".
ALTER TABLE users ALTER COLUMN id TYPE BIGINT;
