package com.example.inventario.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class ReparacionClient {

    private final WebClient reparacionWebClient;

    public ReparacionClient(WebClient reparacionWebClient) {
        this.reparacionWebClient = reparacionWebClient;
    }

    public boolean existeReparacion(Long otId) {
        try {
            reparacionWebClient.get()
                    .uri("/ot/{otId}", otId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            return true;

        } catch (Exception e) {
            log.warn("No existe reparación para OT {}", otId);
            return false;
        }
    }
}
