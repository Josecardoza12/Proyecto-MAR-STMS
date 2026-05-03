package com.example.finanzas.repository;

import com.example.finanzas.model.GastoOperacional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GastoOperacionalRepository extends JpaRepository<GastoOperacional, Long> {

    List<GastoOperacional> findByCategoria(String categoria);
    List<GastoOperacional> findByOtId(Long otId);

}
