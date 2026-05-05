package com.example.pago.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${finanzas.url}")
    private String finanzasUrl;

    @Value("${ot.url}")
    private String otUrl;

    @Bean
    public WebClient finanzasWebClient() {
        return WebClient.builder()
                .baseUrl(finanzasUrl)
                .build();
    }

    @Bean("OtWebClient")
    public WebClient otWebClient() {
        return WebClient.builder()
                .baseUrl(otUrl + "/api/v1/ot")
                .build();
    }
}
