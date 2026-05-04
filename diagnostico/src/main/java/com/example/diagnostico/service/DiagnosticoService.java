package com.example.diagnostico.service;

import com.example.diagnostico.model.Diagnostico;
import com.example.diagnostico.repository.DiagnosticoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class DiagnosticoService {

    private final DiagnosticoRepository repo;

    public DiagnosticoService(DiagnosticoRepository repo) {
        this.repo = repo;
    }

    public Diagnostico crear(Diagnostico d) {
        log.info("Creando diagnóstico para OT {}", d.getOtId());

        d.setFechaDiagnostico(LocalDate.now());
        d.setAprobado("N");

        Diagnostico saved = repo.save(d);

        log.info("Diagnóstico creado con ID {} para OT {}", saved.getDiagnosticoId(), saved.getOtId());
        return saved;
    }

    public Optional<Diagnostico> buscarPorOt(Long otId) {
        log.info("Buscando diagnóstico por OT {}", otId);

        Optional<Diagnostico> result = repo.findByOtId(otId);

        if (result.isPresent()) {
            log.info("Diagnóstico encontrado para OT {}", otId);
        } else {
            log.warn("No existe diagnóstico para OT {}", otId);
        }

        return result;
    }

    public Diagnostico aprobar(Long otId) {
        log.info("Intentando aprobar diagnóstico para OT {}", otId);

        Diagnostico d = repo.findByOtId(otId)
                .orElseThrow(() -> {
                    log.error("No se puede aprobar: diagnóstico para OT {} no existe", otId);
                    return new RuntimeException("Diagnóstico no encontrado");
                });

        d.setAprobado("S");

        Diagnostico updated = repo.save(d);

        log.info("Diagnóstico para OT {} aprobado correctamente", otId);
        return updated;
    }
}
