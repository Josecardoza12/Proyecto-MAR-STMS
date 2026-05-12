package com.example.diagnostico.controller;

import com.example.diagnostico.model.Diagnostico;
import com.example.diagnostico.service.DiagnosticoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/diagnosticos")
@RequiredArgsConstructor
public class DiagnosticoController {

    private final DiagnosticoService service;

    @GetMapping
    public List<Diagnostico> getAll() {
        log.info("GET api/v1/diagnosticos");
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Diagnostico getById(@PathVariable Long id) {
        log.info("GET api/v1/diagnosticos{}", id);
        return service.findById(id);
    }

    @GetMapping("/ot/{otId}")
    public Diagnostico getByOtId(@PathVariable Long otId) {
        log.info("GET api/v1/diagnosticos/ot/{}", otId);
        return service.findByOtId(otId);
    }

    @PostMapping
    public Diagnostico create(
            @Valid @RequestBody Diagnostico diagnostico,
            @RequestHeader("Authorization") String token) {

        log.info("POST api/v1/diagnosticos");
        return service.save(diagnostico, token);
    }

    @PutMapping("/{id}")
    public Diagnostico update(
            @PathVariable Long id,
            @Valid @RequestBody Diagnostico diagnostico,
            @RequestHeader("Authorization") String token) {

        log.info("PUT api/v1/diagnosticos{}", id);
        return service.update(id, diagnostico, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.warn("DELETE api/v1/diagnosticos{}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
