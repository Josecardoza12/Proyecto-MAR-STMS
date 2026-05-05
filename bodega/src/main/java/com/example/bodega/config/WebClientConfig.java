package com.example.bodega.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class WebClientConfig {

    @Value("${pago.url}")
    private String pagoUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(pagoUrl)
                .build();
    }
}
