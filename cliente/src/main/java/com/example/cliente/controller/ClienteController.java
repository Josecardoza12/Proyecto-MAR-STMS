package com.example.cliente.controller;

import com.example.cliente.model.Cliente;
import com.example.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;


    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<Cliente>> listarCliente(){
        List<Cliente> clientes = clienteService.ListarCliente();
        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente){
        Cliente c = clienteService.saveCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente clienteActualizado){
        try{
            Cliente c = clienteService.obtenerPorId(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            c.setNombre(clienteActualizado.getNombre());
            c.setRut(clienteActualizado.getRut());
            c.setTelefono(clienteActualizado.getTelefono());
            c.setDireccion(clienteActualizado.getDireccion());
            c.setTipo_cliente(clienteActualizado.getTipo_cliente());


            clienteService.saveCliente(c);
            return ResponseEntity.ok(clienteActualizado);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            clienteService.deletedCliente(id);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }



}
