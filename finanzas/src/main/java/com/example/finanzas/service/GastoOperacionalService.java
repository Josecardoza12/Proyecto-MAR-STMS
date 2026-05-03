package com.example.finanzas.service;

import com.example.finanzas.exception.GastoOperacionalNotFoundException;
import com.example.finanzas.model.GastoOperacional;
import com.example.finanzas.repository.GastoOperacionalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class GastoOperacionalService {

    @Autowired
    private GastoOperacionalRepository gastoOperacionalRepository;

    public List<GastoOperacional> listarTodos() {
        log.info("Listando todos los gastos operacionales");
        return gastoOperacionalRepository.findAll();
    }

    public GastoOperacional obtenerPorId(Long id) {
        log.info("Buscando gasto operacional con id {}", id);
        return gastoOperacionalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Gasto operacional con id {} no encontrado", id);
                    return new GastoOperacionalNotFoundException(id);
                });
    }

    public List<GastoOperacional> listarPorCategoria(String categoria) {
        log.info("Listando gastos de categoria {}", categoria);
        return gastoOperacionalRepository.findByCategoria(categoria);
    }

    public List<GastoOperacional> listarPorOtId(Long otId) {
        log.info("Listando gastos para OT {}", otId);
        return gastoOperacionalRepository.findByOtId(otId);
    }

    public GastoOperacional registrar(GastoOperacional gasto) {
        log.info("Registrando gasto operacional de categoria {}", gasto.getCategoria());
        gasto.setFecha(LocalDate.now());
        GastoOperacional saved = gastoOperacionalRepository.save(gasto);
        log.info("Gasto registrado con id {}", saved.getId());
        return saved;
    }

    public String eliminar(Long id) {
        log.info("Eliminando gasto con id {}", id);
        obtenerPorId(id);
        gastoOperacionalRepository.deleteById(id);
        log.info("Gasto {} eliminado correctamente", id);
        return "Gasto " + id + " eliminado";
    }

    public Double totalGastos() {
        log.info("Calculando total de gastos operacionales");
        Double total = gastoOperacionalRepository.findAll()
                .stream()
                .mapToDouble(GastoOperacional::getTotal)
                .sum();
        log.info("Total gastos: {}", total);
        return total;
    }
}