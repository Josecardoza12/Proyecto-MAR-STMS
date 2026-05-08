

CREATE TABLE gasto_operacional (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha       DATE,
    categoria   VARCHAR(100),
    detalle     VARCHAR(255),
    proveedor   VARCHAR(100),
    medio_pago  VARCHAR(50),
    total       DOUBLE NOT NULL,
    ot_id       BIGINT
);