package com.example.reparacion.service;

import com.example.reparacion.model.Reparacion;
import com.example.reparacion.repository.ReparacionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReparacionService {

    private final ReparacionRepository repo;

    public ReparacionService(ReparacionRepository repo) {
        this.repo = repo;
    }

    public Reparacion iniciar(Reparacion r) {
        r.setFechaInicio(LocalDate.now());
        r.setEstado("EN_REPARACION");
        return repo.save(r);
    }

    public Reparacion finalizar(Long otId) {
        Reparacion r = repo.findByOtId(otId)
                .orElseThrow(() -> new RuntimeException("Reparación no encontrada"));
        r.setFechaTermino(LocalDate.now());
        r.setEstado("TERMINADA");
        return repo.save(r);
    }

    public Optional<Reparacion> buscarPorOt(Long otId) {
        return repo.findByOtId(otId);
    }
}
