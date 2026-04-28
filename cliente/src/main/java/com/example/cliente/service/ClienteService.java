package com.example.cliente.service;

import com.example.cliente.model.Cliente;
import com.example.cliente.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    //LISTAR TODOS LOS CLIENTES
    public List<Cliente> ListarCliente(){
        return clienteRepository.findAll();
    }

    //OBTENER POR ID
    public Optional<Cliente> obtenerPorId(Long id){
        return clienteRepository.findById(id);
    }

    //Agregar usuarios
    public Cliente saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    //Actualizar, si no encuentra lo pasa
    public Cliente updateCliente(Long id, Cliente clienteActualizado){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id: " + id));

        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setRut(clienteActualizado.getRut());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setTipo_cliente(clienteActualizado.getTipo_cliente());

        return clienteRepository.save(cliente);
    }

    public  String deletedCliente(Long id){
         clienteRepository.deleteById(id);
         return "Cliente " + id + " Eliminado";
    }
}
