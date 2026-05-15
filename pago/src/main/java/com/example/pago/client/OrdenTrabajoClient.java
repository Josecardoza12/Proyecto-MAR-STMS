package com.example.pago.client;

import com.example.pago.model.Orden_Trabajo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    @Qualifier("OrdenTrabajoWebClient")
    private WebClient webClient;

    public Mono<Orden_Trabajo> obtenerOt(Long otId, String token) {
        log.info("Consultando OT {} en ot-service", otId);
        return webClient.get()
                .uri("/{id}", otId)
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Orden de trabajo " + otId + " no encontrada")))
                .bodyToMono(Orden_Trabajo.class);
    }
}
