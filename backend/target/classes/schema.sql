-- ==============================================================================
-- Esquema de Base de Datos - InsecureCare Medical Portal
-- ==============================================================================

DROP TABLE IF EXISTS EXPEDIENTES_MEDICOS;
DROP TABLE IF EXISTS USUARIOS;

CREATE TABLE USUARIOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL, -- [FALLO DE SEGURIDAD]: Contrasenas en texto plano
    nombre_completo VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

CREATE TABLE EXPEDIENTES_MEDICOS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    numero_expediente VARCHAR(20) NOT NULL UNIQUE,
    diagnostico VARCHAR(200) NOT NULL,
    tratamiento VARCHAR(500) NOT NULL,
    observaciones_html CLOB, -- [FALLO DE SEGURIDAD]: Almacena HTML/Scripts sin sanitizar
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confidencial BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_expediente_usuario FOREIGN KEY (usuario_id) REFERENCES USUARIOS(id)
);

