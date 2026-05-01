package com.example.equipo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idCliente")
    private Long idCliente;

    @Column(nullable = false , name = "tipoEquipo")
    private String tipoEquipo;

    @Column(nullable = false, name = "marca")
    private String marca;

    @Column(nullable = false, name = "modelo")
    private String modelo;

    @Column(nullable = false, name = "numeroSerie")
    private String numeroSerie;

    @Column(name = "estadoIngreso", nullable = false)
    private String estadoIngreso;




}
