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
@Table(name = "gasto_operacional")
public class GastoOperacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String categoria;

    private String detalle;

    private String proveedor;

    private String medioPago;

    @Column(nullable = false)
    private Double total;

    private Long otId;
}
