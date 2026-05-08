package com.example.reparacion.controller;

import com.example.reparacion.model.Reparacion;
import com.example.reparacion.service.ReparacionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reparaciones")
public class ReparacionController {

    private final ReparacionService service;

    public ReparacionController(ReparacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Reparacion> iniciar(@Valid @RequestBody Reparacion r) {
        log.info("POST /reparaciones → iniciar reparación para OT {}", r.getOtId());
        Reparacion creada = service.iniciar(r);
        return ResponseEntity.status(201).body(creada);
    }

    @PutMapping("/{otId}/finalizar")
    public ResponseEntity<Reparacion> finalizar(@PathVariable Long otId) {
        log.info("PUT /reparaciones/{}/finalizar", otId);
        return ResponseEntity.ok(service.finalizar(otId));
    }

    @GetMapping("/ot/{otId}")
    public ResponseEntity<Reparacion> buscarPorOt(@PathVariable Long otId) {
        log.info("GET /reparaciones/ot/{}", otId);
        Reparacion r = service.buscarPorOt(otId);
        return ResponseEntity.ok(r);
    }
}
