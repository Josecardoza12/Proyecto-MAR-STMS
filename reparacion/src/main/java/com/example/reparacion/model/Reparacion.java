package com.example.reparacion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reparacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reparacion {

    @Id
    @Column(name = "reparacion_id")
    @NotNull(message = "El ID de reparación es obligatorio")
    private Long reparacionId;

    @Column(name = "ot_id", nullable = false, unique = true)
    @NotNull(message = "El ID de la OT es obligatorio")
    private Long otId;

    @Column(name = "usuario_id", nullable = false)
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_termino")
    private LocalDate fechaTermino;

    @Column(name = "detalle_trabajo")
    @NotBlank(message = "El detalle del trabajo no puede estar vacío")
    private String detalleTrabajo;

    @Column(name = "estado")
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}
