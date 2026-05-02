package com.example.orden_trabajo.controller;

import com.example.orden_trabajo.client.EquipoWebClient;
import com.example.orden_trabajo.client.clienteWebClient;
import com.example.orden_trabajo.exception.OrderTrabajoNotFoundException;
import com.example.orden_trabajo.model.OrdenTrabajo;
import com.example.orden_trabajo.service.OrdenTrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/v1/ot")
public class OrdenTrabajoController {

    @Autowired
    private clienteWebClient clienteWebClient;

    @Autowired
    private EquipoWebClient equipoWebClient;

    @Autowired
    private OrdenTrabajoService ordenTrabajoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<OrdenTrabajo>> ListarOt(){
        List<OrdenTrabajo> ot = ordenTrabajoService.ListarOt();
        if(ot.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ot);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<OrdenTrabajo> obtenerOtPorId(@PathVariable Long id) {
        OrdenTrabajo ot = ordenTrabajoService.obtenerOtPorId(id);
        return ResponseEntity.ok(ot);
    }
    @GetMapping(params = "idCliente")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<OrdenTrabajo>> findByIdCliente(@RequestParam Long idCliente) {
        List<OrdenTrabajo> lista = ordenTrabajoService.findByClienteId(idCliente);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    @GetMapping(params = "idEquipo")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<OrdenTrabajo>> findByIdEquipo(@RequestParam Long idEquipo) {
        List<OrdenTrabajo> lista = ordenTrabajoService.findByIdEquipo(idEquipo);
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<OrdenTrabajo> crearOt(
            @RequestBody OrdenTrabajo ot,
            @RequestHeader("Authorization") String token) {
        clienteWebClient.obtenerCliente(ot.getIdCliente(), token).block();
        equipoWebClient.obtenerEquipo(ot.getIdEquipo(), token).block();
        OrdenTrabajo guardado = ordenTrabajoService.saveOt(ot);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<OrdenTrabajo> actualizarOt(
            @PathVariable Long id,
            @RequestBody OrdenTrabajo otActualizada,
            @RequestHeader("Authorization") String token) {
        try {
            clienteWebClient.obtenerCliente(otActualizada.getIdCliente(), token).block();
            equipoWebClient.obtenerEquipo(otActualizada.getIdEquipo(), token).block();
            OrdenTrabajo ot = ordenTrabajoService.obtenerOtPorId(id);
            ot.setEstado(otActualizada.getEstado());
            ot.setFechaEntrega(otActualizada.getFechaEntrega());
            ot.setDiagnosticoMonto(otActualizada.getDiagnosticoMonto());
            ot.setRepuestosMonto(otActualizada.getRepuestosMonto());
            ot.setManoObraMonto(otActualizada.getManoObraMonto());
            ot.setTotalCobrado(otActualizada.getTotalCobrado());
            ot.setEstadoPago(otActualizada.getEstadoPago());
            ordenTrabajoService.saveOt(ot);
            return ResponseEntity.ok(ot);
        } catch (OrderTrabajoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminarOt(@PathVariable Long id) {
        try {
            ordenTrabajoService.deletedOt(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
