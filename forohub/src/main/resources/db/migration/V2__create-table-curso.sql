CREATE TABLE IF NOT EXISTS cursos(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria ENUM("FRONTEND,BACKEND,IA") NOT NULL
    );