package com.example.pago.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("FinanzasWebClient")
    public WebClient finanzasWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9091/api/v1/finanzas")
                .build();
    }

    @Bean("OrdenTrabajoWebClient")
    public WebClient ordenTrabajoWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8084/api/v1/ot")
                .build();
    }
}

