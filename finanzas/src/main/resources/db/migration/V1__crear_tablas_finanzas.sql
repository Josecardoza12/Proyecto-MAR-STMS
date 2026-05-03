CREATE TABLE movimiento_caja (
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    fecha       DATE,
    tipo        VARCHAR(20) NOT NULL,
    concepto    VARCHAR(255),
    monto       DOUBLE NOT NULL,
    origen      VARCHAR(50),
    ot_id       BIGINT
);

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