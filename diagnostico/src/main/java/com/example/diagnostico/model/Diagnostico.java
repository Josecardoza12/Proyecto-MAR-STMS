package com.example.diagnostico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "diagnostico")
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico {

    @Id
    @Column(name = "diagnostico_id")
    private Long diagnosticoId;

    @Column(name = "ot_id", nullable = false, unique = true)
    private Long otId;

    private String descripcion;
    @Column(name = "causa_probable")
    private String causaProbable;
    @Column(name = "estado")
    private String estado;

    private String aprobado; // 'S' / 'N'

    @Column(name = "fecha_diagnostico", nullable = false)
    private LocalDate fechaDiagnostico;

}
