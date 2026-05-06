CREATE TABLE reparacion (
  reparacion_id BIGINT PRIMARY KEY,
  ot_id BIGINT UNIQUE NOT NULL,
  usuario_id BIGINT,
  fecha_inicio DATE,
  fecha_termino DATE,
  detalle_trabajo VARCHAR(255),
  estado VARCHAR(30)
);
