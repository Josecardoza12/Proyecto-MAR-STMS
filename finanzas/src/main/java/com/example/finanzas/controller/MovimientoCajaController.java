package com.example.finanzas.controller;

import com.example.finanzas.exception.MovimientoCajaNotFoundException;
import com.example.finanzas.model.MovimientoCaja;
import com.example.finanzas.service.MovimientoCajaService;
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
        log.info("GET /api/v1/movimientos - Listando todos los movimientos de caja");
        List<MovimientoCaja> lista = movimientoCajaService.listarTodos();
        if (lista.isEmpty()) {
            log.warn("No hay movimientos de caja registrados");
            return ResponseEntity.noContent().build();
        }
        log.info("Se encontraron {} movimientos de caja", lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovimientoCaja> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/movimientos/{} - Buscando movimiento por id", id);
        try {
            return ResponseEntity.ok(movimientoCajaService.obtenerPorId(id));
        } catch (MovimientoCajaNotFoundException e) {
            log.error("Movimiento con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimientoCaja>> listarPorTipo(@PathVariable String tipo) {
        log.info("GET /api/v1/movimientos/tipo/{} - Listando movimientos por tipo", tipo);
        List<MovimientoCaja> lista = movimientoCajaService.listarPorTipo(tipo);
        if (lista.isEmpty()) {
            log.warn("No hay movimientos de tipo {}", tipo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MovimientoCaja>> listarPorOtId(@PathVariable Long otId) {
        log.info("GET /api/v1/movimientos/ot/{} - Listando movimientos por OT", otId);
        List<MovimientoCaja> lista = movimientoCajaService.listarPorOtId(otId);
        if (lista.isEmpty()) {
            log.warn("No hay movimientos para OT {}", otId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovimientoCaja> registrar(@RequestBody MovimientoCaja movimiento) {
        log.info("POST /api/v1/movimientos - Registrando movimiento de tipo {}", movimiento.getTipo());
        MovimientoCaja m = movimientoCajaService.registrar(movimiento);
        log.info("Movimiento registrado con id {}", m.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(m);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/movimientos/{} - Eliminando movimiento", id);
        try {
            movimientoCajaService.eliminar(id);
            log.info("Movimiento {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (MovimientoCajaNotFoundException e) {
            log.error("Movimiento con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/resumen")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> resumen() {
        log.info("GET /api/v1/movimientos/resumen - Obteniendo resumen financiero");
        return ResponseEntity.ok(Map.of(
                "totalIngresos", movimientoCajaService.totalIngresos(),
                "totalEgresos", movimientoCajaService.totalEgresos(),
                "saldo", movimientoCajaService.saldo()
        ));
    }
}