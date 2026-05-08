package com.example.finanzas.service;

import com.example.finanzas.exception.FinanzasNotFoundException;
import com.example.finanzas.model.Finanzas;
import com.example.finanzas.repository.FinanzasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class FinanzasService {

    @Autowired
    private FinanzasRepository finanzasRepository;

    public List<Finanzas> listarTodos() {
        log.info("Listando todos los gastos operacionales");
        return finanzasRepository.findAll();
    }

    public Finanzas obtenerPorId(Long id) {
        log.info("Buscando gasto operacional con id {}", id);
        return finanzasRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Gasto operacional con id {} no encontrado", id);
                    return new FinanzasNotFoundException(id);
                });
    }

    public List<Finanzas> listarPorCategoria(String categoria) {
        log.info("Listando gastos de categoria {}", categoria);
        return finanzasRepository.findByCategoria(categoria);
    }

    public List<Finanzas> listarPorOtId(Long otId) {
        log.info("Listando gastos para OT {}", otId);
        return finanzasRepository.findByOtId(otId);
    }

    public Finanzas registrar(Finanzas gasto) {
        log.info("Registrando gasto operacional de categoria {}", gasto.getCategoria());
        gasto.setFecha(LocalDate.now());
        Finanzas saved = finanzasRepository.save(gasto);
        log.info("Gasto registrado con id {}", saved.getId());
        return saved;
    }

    public String eliminar(Long id) {
        log.info("Eliminando gasto con id {}", id);
        obtenerPorId(id);
        finanzasRepository.deleteById(id);
        log.info("Gasto {} eliminado correctamente", id);
        return "Gasto " + id + " eliminado";
    }

    public Double totalGastos() {
        log.info("Calculando total de gastos operacionales");
        Double total = finanzasRepository.findAll()
                .stream()
                .mapToDouble(Finanzas::getTotal)
                .sum();
        log.info("Total gastos: {}", total);
        return total;
    }
}