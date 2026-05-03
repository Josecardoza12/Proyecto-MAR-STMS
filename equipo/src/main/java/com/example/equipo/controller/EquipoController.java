package com.example.equipo.controller;

import com.example.equipo.client.ClienteClient;
import com.example.equipo.exception.EquipoNotFoundException;
import com.example.equipo.model.Equipo;
import com.example.equipo.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")

public class EquipoController {
    @Autowired
    private  ClienteClient clienteClient;
    @Autowired
    private  EquipoService equipoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<Equipo>> listarEquipo(){
        List<Equipo> equipo = equipoService.ListarEquipo();
        if(equipo.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Long id) {
        Equipo equipo = equipoService.obtenerPorId(id);
        return ResponseEntity.ok(equipo);
    }

    //buscar por cliente
    @GetMapping(params = "idCliente")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<Equipo>> findByIdCliente(@RequestParam Long idCliente) {
        List<Equipo> equipos = equipoService.findByClienteId(idCliente);
        if (equipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipos);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo, @RequestHeader("Authorization") String token) {
        clienteClient.obtenerCliente(equipo.getIdCliente(), token).block();
        Equipo guardado = equipoService.saveEquipo(equipo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody Equipo equipoActualizado,
                                             @RequestHeader("Authorization") String token) {
        try {
            clienteClient.obtenerCliente(equipoActualizado.getIdCliente(), token).block();
            Equipo equipo = equipoService.obtenerPorId(id);

            equipo.setTipoEquipo(equipoActualizado.getTipoEquipo());
            equipo.setModelo(equipoActualizado.getModelo());
            equipo.setMarca(equipoActualizado.getMarca());
            equipo.setNumeroSerie(equipoActualizado.getNumeroSerie());
            equipo.setEstadoIngreso(equipoActualizado.getEstadoIngreso());

            equipoService.saveEquipo(equipo);
            return ResponseEntity.ok(equipo);
        } catch (EquipoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> eliminarEquipo(@PathVariable Long id){
        try{
            equipoService.deletedEquipo(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



}

