package com.example.orden_trabajo.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean("ClienteWebClient")
    @LoadBalanced
    public WebClient clienteWebClient() {
        return WebClient.builder()
                .baseUrl("http://cliente/api/v1/clientes")
                .build();
    }

    @Bean("EquipoWebClient")
    @LoadBalanced
    public WebClient equipoWebClient() {
        return WebClient.builder()
                .baseUrl("http://equipo/api/v1/equipos")
                .build();
    }

}
