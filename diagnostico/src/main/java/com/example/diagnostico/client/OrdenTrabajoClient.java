package com.example.diagnostico.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrdenTrabajoClient {

    private final WebClient.Builder webClientBuilder;

    public boolean existeOt(Long otId, String token) {

        try {
            webClientBuilder.build()
                    .get()
                    .uri("http://orden_trabajo/api/v1/ot/" + otId)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();

            log.info("OT {} existe", otId);
            return true;

        } catch (Exception e) {
            log.warn("OT {} NO existe o error al consultar: {}", otId, e.getMessage());
            return false;
        }
    }
}
