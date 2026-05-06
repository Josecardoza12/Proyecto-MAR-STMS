CREATE TABLE IF NOT EXISTS repuesto (
    repuesto_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    proveedor VARCHAR(100),
    costo DECIMAL(10,2),
    precio_sugerido DECIMAL(10,2),
    stock INT
);

CREATE TABLE IF NOT EXISTS movimiento_repuesto (
    movimiento_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    repuesto_id BIGINT NOT NULL,
    ot_id BIGINT NOT NULL,
    tipo VARCHAR(10),
    cantidad INT,
    fecha DATE,
    CONSTRAINT fk_mov_rep_repuesto
        FOREIGN KEY (repuesto_id) REFERENCES repuesto(repuesto_id)
);
