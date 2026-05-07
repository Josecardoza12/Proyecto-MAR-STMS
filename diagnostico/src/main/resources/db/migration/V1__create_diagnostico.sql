CREATE TABLE IF NOT EXISTS diagnostico (
    diagnostico_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(255),
    causa_probable VARCHAR(255),
    hallazgo VARCHAR(20),
    estado CHAR(1),
    fecha_diagnostico DATE,
    orden_trabajo_ot_id BIGINT NOT NULL
);

