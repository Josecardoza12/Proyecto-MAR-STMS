package com.example.pago.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${finanzas.url}")
    private String finanzasUrl;

    @Bean
    public WebClient finanzasWebClient() {
        return WebClient.builder()
                .baseUrl(finanzasUrl)
                .build();
    }
}
