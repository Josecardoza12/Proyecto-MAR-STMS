package com.example.finanzas.controller;

import com.example.finanzas.exception.FinanzasNotFoundException;
import com.example.finanzas.model.Finanzas;
import com.example.finanzas.service.FinanzasService;
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
@RequestMapping("/api/v1/finanzas")
public class FinanzasController {

    @Autowired
    private FinanzasService finanzasService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Finanzas>> listarTodos() {
        List<Finanzas> lista = finanzasService.listarTodos();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Finanzas> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(finanzasService.obtenerPorId(id));
        } catch (FinanzasNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categoria/{categoria}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Finanzas>> listarPorCategoria(@PathVariable String categoria) {
        List<Finanzas> lista = finanzasService.listarPorCategoria(categoria);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Finanzas>> listarPorOtId(@PathVariable Long otId) {
        List<Finanzas> lista = finanzasService.listarPorOtId(otId);
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Finanzas> registrar(@Valid @RequestBody Finanzas gasto) {
        log.info("POST /api/v1/gastos - Registrando gasto categoria {}", gasto.getCategoria());
        Finanzas g = finanzasService.registrar(gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            finanzasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (FinanzasNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> total() {
        return ResponseEntity.ok(Map.of(
                "totalGastos", finanzasService.totalGastos()
        ));
    }
}