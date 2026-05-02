package com.example.orden_trabajo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orden_trabajo")
public class OrdenTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCliente;

    private Long idEquipo;

    @Column(nullable = false , unique = true)
    private Integer numeroOt;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private Date fechaIngreso;

    private Date fechaEntrega;

    private Double diagnosticoMonto;

    private Double repuestosMonto;

    private Double manoObraMonto;

    private Double totalCobrado;

    @Column(nullable = false)
    private String estadoPago;











}
