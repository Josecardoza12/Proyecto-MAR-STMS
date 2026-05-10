package com.example.inventario.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class EquipoClient {

    private final WebClient equipoWebClient;

    public EquipoClient(WebClient equipoWebClient) {
        this.equipoWebClient = equipoWebClient;
    }

    public boolean existeEquipo(Long id) {
        try {
            equipoWebClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            return true;

        } catch (Exception e) {
            log.warn("No existe equipo con id {}", id);
            return false;
        }
    }
}
