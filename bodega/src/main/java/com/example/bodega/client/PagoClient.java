package com.example.bodega.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class PagoClient {

    @Autowired
    private RestClient restClient;

    public void registrarPagoBodega(String token, Long otId, Double monto) {
        try {
            Map<String, Object> pago = new HashMap<>();
            pago.put("otId", otId);
            pago.put("monto", monto);
            pago.put("formaPago", "bodegaje");

            restClient.post()
                    .uri("/api/v1/pagos")
                    .header("Authorization", token)
                    .body(pago)
                    .retrieve()
                    .toBodilessEntity();

            log.info("Pago de bodegaje registrado en pago para OT {}", otId);
        } catch (Exception e) {
            log.error("Error al registrar pago de bodegaje para OT {}: {}", otId, e.getMessage());
        }
    }
}
