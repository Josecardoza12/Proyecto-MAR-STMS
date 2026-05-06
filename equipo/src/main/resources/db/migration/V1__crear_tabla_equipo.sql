CREATE TABLE equipo(
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     idCliente  BIGINT,
     tipoEquipo VARCHAR(100),
     marca VARCHAR(50),
      modelo VARCHAR(50),
     numeroSerie VARCHAR(100),
     estadoIngreso VARCHAR(50)
)