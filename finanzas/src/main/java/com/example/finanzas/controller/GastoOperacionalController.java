package com.example.finanzas.controller;

import com.example.finanzas.exception.GastoOperacionalNotFoundException;
import com.example.finanzas.model.GastoOperacional;
import com.example.finanzas.service.GastoOperacionalService;
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
@RequestMapping("/api/v1/gastos")
public class GastoOperacionalController {

    @Autowired
    private GastoOperacionalService gastoOperacionalService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GastoOperacional>> listarTodos() {
        List<GastoOperacional> lista = gastoOperacionalService.listarTodos();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GastoOperacional> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(gastoOperacionalService.obtenerPorId(id));
        } catch (GastoOperacionalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categoria/{categoria}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GastoOperacional>> listarPorCategoria(@PathVariable String categoria) {
        List<GastoOperacional> lista = gastoOperacionalService.listarPorCategoria(categoria);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GastoOperacional>> listarPorOtId(@PathVariable Long otId) {
        List<GastoOperacional> lista = gastoOperacionalService.listarPorOtId(otId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GastoOperacional> registrar(@Valid @RequestBody GastoOperacional gasto) {
        log.info("POST /api/v1/gastos - Registrando gasto categoria {}", gasto.getCategoria());
        GastoOperacional g = gastoOperacionalService.registrar(gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            gastoOperacionalService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (GastoOperacionalNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> total() {
        return ResponseEntity.ok(Map.of(
                "totalGastos", gastoOperacionalService.totalGastos()
        ));
    }
}