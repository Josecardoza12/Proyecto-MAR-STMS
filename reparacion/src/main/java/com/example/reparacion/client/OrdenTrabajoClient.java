package com.example.reparacion.client;

import com.example.reparacion.model.Ot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OrdenTrabajoClient {

    private final WebClient webClient;

    public OrdenTrabajoClient(@Qualifier("OrdenTrabajoWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Ot> obtenerOt(Long id, String token) {
        log.info("Consultando OT {} en ordenTrabajo", id);

        return webClient.get()
                .uri("/{id}", id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "OT no encontrada")))
                .bodyToMono(Ot.class)
                .doOnError(e -> log.error("Error consultando OT {}: {}", id, e.getMessage()));
    }
}
