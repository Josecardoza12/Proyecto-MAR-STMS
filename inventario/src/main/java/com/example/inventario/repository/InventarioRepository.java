package com.example.inventario.repository;

import com.example.inventario.model.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepuestoRepository extends JpaRepository< Repuesto, Long> {
}

