package com.example.inventario.model;


import lombok.Data;

@Data
public class Equipo {

    private Long id;
    private Long idCliente;
    private String tipoEquipo;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String estadoIngreso;
}
