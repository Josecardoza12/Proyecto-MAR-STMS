package com.example.inventario.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${reparacion.url}")
    private String reparacionUrl;

    @Value("${equipo.url}")
    private String equipoUrl;

    @Bean("ReparacionWebClient")
    public WebClient reparacionWebClient() {
        return WebClient.builder()
                .baseUrl(reparacionUrl + "/reparaciones")
                .build();
    }

    @Bean("EquipoWebClient")
    public WebClient equipoWebClient() {
        return WebClient.builder()
                .baseUrl(equipoUrl + "/equipos")
                .build();
    }
}

