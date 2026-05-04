package com.example.diagnostico.controller;

import com.example.diagnostico.model.Diagnostico;
import com.example.diagnostico.service.DiagnosticoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diagnosticos")
public class DiagnosticoController {

    private final DiagnosticoService service;

    public DiagnosticoController(DiagnosticoService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
    public ResponseEntity<Diagnostico> crear(@RequestBody Diagnostico d) {
        return ResponseEntity.ok(service.crear(d));
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasAnyRole('TECNICO','ADMIN')")
    public ResponseEntity<Diagnostico> buscarPorOt(@PathVariable Long otId) {
        return service.buscarPorOt(otId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{otId}/aprobar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Diagnostico> aprobar(@PathVariable Long otId) {
        return ResponseEntity.ok(service.aprobar(otId));
    }
}
