package com.example.bodega.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bodega")
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long otId;

    private LocalDate fechaListo;

    private Integer diasEnBodega;

    // "sin_cobro" o "con_cobro"
    private String estadoCobro;

    private Double montoBodegaje;
}
