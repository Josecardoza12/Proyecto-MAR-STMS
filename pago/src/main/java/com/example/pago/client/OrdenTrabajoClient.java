package com.example.pago.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrdenTrabajoClient {

    @Autowired
    @Qualifier("OtWebClient")
    private WebClient webClient;

    public Mono<Void> validarOt(Long otId, String token) {
        return webClient.get()
                .uri("/{id}", otId)
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Orden de trabajo " + otId + " no encontrada")))
                .bodyToMono(Void.class);
    }
}
