package com.example.pago.controller;

import com.example.pago.exception.PagoNotFoundException;
import com.example.pago.model.Pago;
import com.example.pago.service.PagoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<List<Pago>> listarTodos() {
        log.info("GET /api/v1/pagos - Listando todos los pagos");
        List<Pago> lista = pagoService.listarTodos();
        if (lista.isEmpty()) {
            log.warn("No hay pagos registrados");
            return ResponseEntity.noContent().build();
        }
        log.info("Se encontraron {} pagos", lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<Pago> obtenerPorId(@PathVariable Long id) {
        log.info("GET /api/v1/pagos/{} - Buscando pago por id", id);
        try {
            Pago pago = pagoService.obtenerPorId(id);
            log.info("Pago encontrado con id {}", id);
            return ResponseEntity.ok(pago);
        } catch (PagoNotFoundException e) {
            log.error("Pago con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ot/{otId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO', 'CLIENTE')")
    public ResponseEntity<List<Pago>> obtenerPorOtId(@PathVariable Long otId) {
        log.info("GET /api/v1/pagos/ot/{} - Buscando pagos por OT", otId);
        List<Pago> pagos = pagoService.obtenerPorOtId(otId);
        if (pagos.isEmpty()) {
            log.warn("No hay pagos para la OT {}", otId);
            return ResponseEntity.noContent().build();
        }
        log.info("Se encontraron {} pagos para OT {}", pagos.size(), otId);
        return ResponseEntity.ok(pagos);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<Pago> registrar(@RequestBody Pago pago) {
        log.info("POST /api/v1/pagos - Registrando pago para OT {}", pago.getOtId());
        Pago p = pagoService.registrar(pago);
        log.info("Pago registrado con id {}", p.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECNICO')")
    public ResponseEntity<Pago> actualizar(@PathVariable Long id, @RequestBody Pago pagoActualizado) {
        log.info("PUT /api/v1/pagos/{} - Actualizando pago", id);
        try {
            Pago p = pagoService.actualizar(id, pagoActualizado);
            log.info("Pago {} actualizado correctamente", id);
            return ResponseEntity.ok(p);
        } catch (PagoNotFoundException e) {
            log.error("Pago con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/v1/pagos/{} - Eliminando pago", id);
        try {
            pagoService.eliminar(id);
            log.info("Pago {} eliminado correctamente", id);
            return ResponseEntity.noContent().build();
        } catch (PagoNotFoundException e) {
            log.error("Pago con id {} no encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }
}