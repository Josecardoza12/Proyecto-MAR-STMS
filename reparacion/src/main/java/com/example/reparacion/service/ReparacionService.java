package com.example.reparacion.service;

import com.example.reparacion.client.OrdenTrabajoClient;
import com.example.reparacion.exception.ReparacionNotFoundException;
import com.example.reparacion.model.Reparacion;
import com.example.reparacion.repository.ReparacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ReparacionService {

    private final ReparacionRepository repo;
    private final OrdenTrabajoClient otClient;

    public ReparacionService(ReparacionRepository repo, OrdenTrabajoClient otClient) {
        this.repo = repo;
        this.otClient = otClient;
    }

    public List<Reparacion> listar() {
        return repo.findAll();
    }

    public Reparacion buscarPorOt(Long otId) {
        return repo.findByOtId(otId)
                .orElseThrow(() -> new ReparacionNotFoundException(otId));
    }

    public Reparacion iniciar(Reparacion r) {

        if (!otClient.existeOt(r.getOtId())) {
            throw new IllegalStateException("La OT no existe");
        }

        if (repo.findByOtId(r.getOtId()).isPresent()) {
            throw new IllegalStateException("La OT ya tiene una reparación");
        }

        r.setFechaInicio(LocalDate.now());
        r.setEstado(Reparacion.EstadoReparacion.EN_REPARACION);

        return repo.save(r);
    }

    public Reparacion finalizar(Long otId) {

        Reparacion r = buscarPorOt(otId);

        if (r.getEstado() == Reparacion.EstadoReparacion.TERMINADA) {
            throw new IllegalStateException("La reparación ya está terminada");
        }

        r.setFechaTermino(LocalDate.now());
        r.setEstado(Reparacion.EstadoReparacion.TERMINADA);

        return repo.save(r);
    }

    public void eliminar(Long otId) {
        Reparacion r = buscarPorOt(otId);
        repo.delete(r);
    }
}
