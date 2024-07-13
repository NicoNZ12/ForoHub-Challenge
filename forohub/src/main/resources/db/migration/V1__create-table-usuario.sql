CREATE TABLE IF NOT EXISTS usuarios(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    activo TINYINT NOT NULL
);