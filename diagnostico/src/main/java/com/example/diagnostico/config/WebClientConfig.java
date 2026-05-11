package com.example.diagnostico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient ordenTrabajoWebClient() {
        return WebClient.builder()
                .baseUrl("http://ordentrabajo-service")
                .build();
    }
}
