CREATE TABLE diagnostico (
  diagnostico_id BIGINT PRIMARY KEY,
  ot_id BIGINT UNIQUE NOT NULL,
  descripcion VARCHAR(255),
  causa_probable VARCHAR(255),
  nivel_dano VARCHAR(20),
  aprobado CHAR(1),
  fecha_diagnostico DATE NOT NULL
);

