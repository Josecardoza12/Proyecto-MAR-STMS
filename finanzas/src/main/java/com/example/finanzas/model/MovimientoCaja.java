package com.example.finanzas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movimiento_caja")
public class MovimientoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    // "ingreso" o "egreso"
    @Column(nullable = false)
    private String tipo;

    private String concepto;

    @Column(nullable = false)
    private Double monto;

    // "orden", "inversion", "gasto"
    private String origen;

    private Long otId;
}
