package com.example.pago.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Finanzas {

    private Long id;

    private LocalDate fecha;

    private String categoria;

    private String detalle;

    private String proveedor;

    private String medioPago;

    private Double total;

    private Long otId;
}
