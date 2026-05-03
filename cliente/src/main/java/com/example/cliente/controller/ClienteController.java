package com.example.cliente.controller;

import com.example.cliente.exception.ClienteNotFoundException;
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



    @GetMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<List<Cliente>> listarCliente(){
        List<Cliente> clientes = clienteService.ListarCliente();
        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente){
        Cliente c = clienteService.saveCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        try {
            Cliente c = clienteService.obtenerPorId(id);

            c.setNombre(clienteActualizado.getNombre());
            c.setRut(clienteActualizado.getRut());
            c.setTelefono(clienteActualizado.getTelefono());
            c.setDireccion(clienteActualizado.getDireccion());
            c.setTipo_Cliente(clienteActualizado.getTipo_Cliente());

            clienteService.saveCliente(c);
            return ResponseEntity.ok(c); // devuelve c, no clienteActualizado
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN')")

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
