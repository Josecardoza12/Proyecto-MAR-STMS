package com.example.equipo.controller;

import com.example.equipo.client.ClienteClient;
import com.example.equipo.exception.EquipoNotFoundException;
import com.example.equipo.model.Equipo;
import com.example.equipo.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Equipo>> listarEquipo(){
        List<Equipo> equipo = equipoService.ListarEquipo();
        if(equipo.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerPorId(@PathVariable Long id) {
        Equipo equipo = equipoService.obtenerPorId(id);
        return ResponseEntity.ok(equipo);
    }

    //buscar por cliente
    @GetMapping(params = "idCliente")
    public ResponseEntity<List<Equipo>> findByIdCliente(@RequestParam Long idCliente) {
        List<Equipo> equipos = equipoService.findByClienteId(idCliente);
        if (equipos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(equipos);
    }

    @PostMapping
    public Mono<ResponseEntity<Equipo>> crearEquipo(@RequestBody Equipo equipo) {
        System.out.println(equipo);
        return clienteClient.obtenerCliente(equipo.getIdCliente())
                .map(cliente -> {
                    System.out.println(cliente);
                    Equipo guardado = equipoService.saveEquipo(equipo);
                    return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @RequestBody Equipo equipoActualizado) {
        try {
            clienteClient.obtenerCliente(equipoActualizado.getIdCliente()).block();
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
    public ResponseEntity<?> eliminarEquipo(@PathVariable Long id){
        try{
            equipoService.deletedEquipo(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



}

