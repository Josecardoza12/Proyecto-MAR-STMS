package com.example.finanzas.controller;

import com.example.finanzas.exception.MovimientoCajaNotFoundException;
import com.example.finanzas.model.MovimientoCaja;
import com.example.finanzas.service.MovimientoCajaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/movimientos")
public class MovimientoCajaController {

    @Autowired
    private MovimientoCajaService movimientoCajaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimientoCaja>> listarTodos() {
        log.info("GET /api/v1/movimientos - Listando todos los movimientos");
        List<MovimientoCaja> lista = movimientoCajaService.listarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovimientoCaja> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/movimientos/{}", id);
        try {
            return ResponseEntity.ok(movimientoCajaService.obtenerPorId(id));
        } catch (MovimientoCajaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimientoCaja>> listarPorTipo(@PathVariable String tipo) {
        List<MovimientoCaja> lista = movimientoCajaService.listarPorTipo(tipo);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimientoCaja>> listarPorOtId(@PathVariable Long otId) {
        List<MovimientoCaja> lista = movimientoCajaService.listarPorOtId(otId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<MovimientoCaja> registrar(@Valid @RequestBody MovimientoCaja movimiento) {
        log.info("POST /api/v1/movimientos - Registrando movimiento tipo {}", movimiento.getTipo());
        MovimientoCaja m = movimientoCajaService.registrar(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            movimientoCajaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (MovimientoCajaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> resumen() {
        return ResponseEntity.ok(Map.of(
                "totalIngresos", movimientoCajaService.totalIngresos(),
                "totalEgresos", movimientoCajaService.totalEgresos(),
                "saldo", movimientoCajaService.saldo()
        ));
    }
}