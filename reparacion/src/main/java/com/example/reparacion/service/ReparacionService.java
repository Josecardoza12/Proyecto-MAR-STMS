package com.example.reparacion.service;

import com.example.reparacion.client.OrdenTrabajoClient;
import com.example.reparacion.exception.ReparacionNotFoundException;
import com.example.reparacion.model.Reparacion;
import com.example.reparacion.repository.ReparacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReparacionService {

    private final ReparacionRepository repository;
    private final OrdenTrabajoClient otClient;

    public List<Reparacion> findAll() {
        log.info("Listando todas las reparaciones");
        return repository.findAll();
    }

    public Reparacion findById(Long id) {
        log.info("Buscando reparación {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ReparacionNotFoundException(id));
    }

    public Reparacion save(Reparacion reparacion, String token) {

        log.info("Validando existencia de OT {}", reparacion.getOtId());
        otClient.obtenerOt(reparacion.getOtId(), token).block();

        log.info("Guardando reparación {}", reparacion);
        return repository.save(reparacion);
    }

    public Reparacion update(Long id, Reparacion reparacion, String token) {

        log.info("Actualizando reparación {}", id);

        Reparacion existing = findById(id);

        otClient.obtenerOt(reparacion.getOtId(), token).block();

        existing.setOtId(reparacion.getOtId());
        existing.setUsuarioId(reparacion.getUsuarioId());
        existing.setFechaInicio(reparacion.getFechaInicio());
        existing.setFechaTermino(reparacion.getFechaTermino());
        existing.setDetalleTrabajo(reparacion.getDetalleTrabajo());
        existing.setEstado(reparacion.getEstado());

        return repository.save(existing);
    }

    public void delete(Long id) {
        log.warn("Eliminando reparación {}", id);
        repository.delete(findById(id));
    }
}
