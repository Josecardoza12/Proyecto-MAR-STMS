package com.example.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean("ReparacionWebClient")
    public WebClient reparacionWebClient() {
        return WebClient.builder()
                .baseUrl("http://reparacion/api/v1/reparaciones")
                .build();
    }

    @Bean("EquipoWebClient")
    public WebClient equipoWebClient() {
        return WebClient.builder()
                .baseUrl("http://equipo/api/v1/equipos")
                .build();
    }
}
