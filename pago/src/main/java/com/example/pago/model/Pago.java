package com.example.pago.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long otId;

    private LocalDate fecha;

    @Column(nullable = false)
    private Double monto;

    // "efectivo", "transferencia", "debito", "credito"
    private String formaPago;

    // "pendiente" o "pagado"
    private String estado;
}