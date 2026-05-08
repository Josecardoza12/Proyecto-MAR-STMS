package com.example.reparacion.client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrdenTrabajoClient {

    private final WebClient webClient;

    public OrdenTrabajoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public boolean existeOt(Long otId) {

        log.info("Consultando existencia de OT {} en microservicio OT", otId);

        try {
            webClient.get()
                    .uri("http://orden-trabajo-service/api/v1/ordenes/" + otId)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .onErrorResume(e -> {
                        log.error("Error consultando OT {}: {}", otId, e.getMessage());
                        return Mono.empty();
                    })
                    .block();

            log.info("OT {} existe", otId);
            return true;

        } catch (Exception e) {
            log.error("Fallo crítico consultando OT {}: {}", otId, e.getMessage());
            return false;
        }
    }
}
