package com.example.finanzas.repository;

import com.example.finanzas.model.MovimientoCaja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {

    List<MovimientoCaja> findByTipo(String tipo);
    List<MovimientoCaja> findByOtId(Long otId);

}
