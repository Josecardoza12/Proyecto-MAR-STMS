CREATE TABLE IF NOT EXISTS repuesto (
    repuesto_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100),
    proveedor VARCHAR(100),
    costo DECIMAL(10,2),
    precio_sugerido DECIMAL(10,2),
    stock INT
);
