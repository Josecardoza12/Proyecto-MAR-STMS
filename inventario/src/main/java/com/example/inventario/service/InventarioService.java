package com.example.inventario.service;

import com.example.inventario.model.Repuesto;
import com.example.inventario.repository.RepuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final RepuestoRepository repository;

    public InventarioService(RepuestoRepository repository) {
        this.repository = repository;
    }

    public List<Repuesto> findAll() {
        return repository.findAll();
    }

    public Optional<Repuesto> findById(Long id) {
        return repository.findById(id);
    }

    public Repuesto save(Repuesto repuesto) {
        return repository.save(repuesto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
