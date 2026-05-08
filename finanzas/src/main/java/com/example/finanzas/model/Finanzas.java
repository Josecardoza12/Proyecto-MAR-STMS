package com.example.finanzas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gasto_operacional")
public class Finanzas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String categoria;

    private String detalle;

    private String proveedor;

    private String medioPago;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a 0")
    @Column(nullable = false)
    private Double total;

    private Long otId;
}