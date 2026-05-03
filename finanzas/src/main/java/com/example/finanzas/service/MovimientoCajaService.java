package com.example.finanzas.service;

import com.example.finanzas.exception.MovimientoCajaNotFoundException;
import com.example.finanzas.model.MovimientoCaja;
import com.example.finanzas.repository.MovimientoCajaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class MovimientoCajaService {

    @Autowired
    private MovimientoCajaRepository movimientoCajaRepository;

    public List<MovimientoCaja> listarTodos() {
        log.info("Listando todos los movimientos de caja");
        return movimientoCajaRepository.findAll();
    }

    public MovimientoCaja obtenerPorId(Long id) {
        log.info("Buscando movimiento de caja con id {}", id);
        return movimientoCajaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Movimiento de caja con id {} no encontrado", id);
                    return new MovimientoCajaNotFoundException(id);
                });
    }

    public List<MovimientoCaja> listarPorTipo(String tipo) {
        log.info("Listando movimientos de tipo {}", tipo);
        return movimientoCajaRepository.findByTipo(tipo);
    }

    public List<MovimientoCaja> listarPorOtId(Long otId) {
        log.info("Listando movimientos para OT {}", otId);
        return movimientoCajaRepository.findByOtId(otId);
    }

    public MovimientoCaja registrar(MovimientoCaja movimiento) {
        log.info("Registrando movimiento de tipo {}", movimiento.getTipo());
        movimiento.setFecha(LocalDate.now());
        MovimientoCaja saved = movimientoCajaRepository.save(movimiento);
        log.info("Movimiento registrado con id {}", saved.getId());
        return saved;
    }

    public String eliminar(Long id) {
        log.info("Eliminando movimiento con id {}", id);
        obtenerPorId(id);
        movimientoCajaRepository.deleteById(id);
        log.info("Movimiento {} eliminado correctamente", id);
        return "Movimiento " + id + " eliminado";
    }

    public Double totalIngresos() {
        log.info("Calculando total de ingresos");
        return movimientoCajaRepository.findByTipo("ingreso")
                .stream()
                .mapToDouble(MovimientoCaja::getMonto)
                .sum();
    }

    public Double totalEgresos() {
        log.info("Calculando total de egresos");
        return movimientoCajaRepository.findByTipo("egreso")
                .stream()
                .mapToDouble(MovimientoCaja::getMonto)
                .sum();
    }

    public Double saldo() {
        Double saldo = totalIngresos() - totalEgresos();
        log.info("Saldo actual: {}", saldo);
        return saldo;
    }
}