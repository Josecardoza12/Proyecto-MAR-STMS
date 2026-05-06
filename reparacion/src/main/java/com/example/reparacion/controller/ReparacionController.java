package com.mar.reparacion.controller;

import com.example.reparacion.model.Reparacion;
import com.example.reparacion.service.ReparacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reparaciones")
public class ReparacionController {

    private final ReparacionService service;

    public ReparacionController(ReparacionService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<Reparacion> iniciar(@RequestBody Reparacion r) {
        return ResponseEntity.ok(service.iniciar(r));
    }

    @PutMapping("/{otId}/finalizar")
    @PreAuthorize("hasRole('TECNICO')")
    public ResponseEntity<Reparacion> finalizar(@PathVariable Long otId) {
        return ResponseEntity.ok(service.finalizar(otId));
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
    public ResponseEntity<Reparacion> buscarPorOt(@PathVariable Long otId) {
        return service.buscarPorOt(otId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
