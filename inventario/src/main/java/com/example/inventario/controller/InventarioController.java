package com.example.inventario.controller;

import com.example.inventario.model.Repuesto;
import com.example.inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Repuesto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repuesto> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Repuesto> create(@Valid @RequestBody Repuesto repuesto) {
        return ResponseEntity.ok(service.save(repuesto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Repuesto> update(
            @PathVariable Long id,
            @Valid @RequestBody Repuesto repuesto) {

        return service.findById(id)
                .map(existing -> {
                    repuesto.setRepuestoId(id);
                    return ResponseEntity.ok(service.save(repuesto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
