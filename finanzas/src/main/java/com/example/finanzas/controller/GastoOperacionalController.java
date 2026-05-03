package com.example.finanzas.controller;

import com.example.finanzas.exception.GastoOperacionalNotFoundException;
import com.example.finanzas.model.GastoOperacional;
import com.example.finanzas.service.GastoOperacionalService;
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
@RequestMapping("/api/v1/gastos")
public class GastoOperacionalController {

    @Autowired
    private GastoOperacionalService gastoOperacionalService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GastoOperacional>> listarTodos() {
        log.info("GET /api/v1/gastos - Listando todos los gastos operacionales");
        List<GastoOperacional> lista = gastoOperacionalService.listarTodos();
        if (lista.isEmpty()) {
            log.warn("No hay gastos operacionales registrados");
            return ResponseEntity.noContent().build();
        }
        log.info("Se encontraron {} gastos operacionales", lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GastoOperacional> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/gastos/{} - Buscando gasto por id", id);
        try {
            return ResponseEntity.ok(gastoOperacionalService.obtenerPorId(id));
        } catch (GastoOperacionalNotFoundException e) {
            log.error("Gasto con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categoria/{categoria}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GastoOperacional>> listarPorCategoria(@PathVariable String categoria) {
        log.info("GET /api/v1/gastos/categoria/{} - Listando gastos por categoria", categoria);
        List<GastoOperacional> lista = gastoOperacionalService.listarPorCategoria(categoria);
        if (lista.isEmpty()) {
            log.warn("No hay gastos para la categoria {}", categoria);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GastoOperacional>> listarPorOtId(@PathVariable Long otId) {
        log.info("GET /api/v1/gastos/ot/{} - Listando gastos por OT", otId);
        List<GastoOperacional> lista = gastoOperacionalService.listarPorOtId(otId);
        if (lista.isEmpty()) {
            log.warn("No hay gastos para OT {}", otId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GastoOperacional> registrar(@RequestBody GastoOperacional gasto) {
        log.info("POST /api/v1/gastos - Registrando gasto operacional de categoria {}", gasto.getCategoria());
        GastoOperacional g = gastoOperacionalService.registrar(gasto);
        log.info("Gasto registrado con id {}", g.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/gastos/{} - Eliminando gasto", id);
        try {
            gastoOperacionalService.eliminar(id);
            log.info("Gasto {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (GastoOperacionalNotFoundException e) {
            log.error("Gasto con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> total() {
        log.info("GET /api/v1/gastos/total - Obteniendo total de gastos");
        return ResponseEntity.ok(Map.of(
                "totalGastos", gastoOperacionalService.totalGastos()
        ));
    }
}