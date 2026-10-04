CREATE TABLE roles (
    id          SMALLINT NOT NULL AUTO_INCREMENT,
    created_at  DATETIME(6) NOT NULL,
    description VARCHAR(255) DEFAULT NULL,
    name        VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UKofx66keruapi6vyqpv6f2or37 UNIQUE (name)
);

CREATE TABLE users (
    id         INT NOT NULL AUTO_INCREMENT,
    is_enabled BIT(1) NOT NULL,
    role_id    SMALLINT DEFAULT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    password   VARCHAR(255) DEFAULT NULL,
    username   VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT UKr43af9ap4edm43mmtq01oddj6 UNIQUE (username),
    CONSTRAINT FKp56c1712k691lhsyewcssf40f FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE INDEX FKp56c1712k691lhsyewcssf40f ON users (role_id);
