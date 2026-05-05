package com.example.bodega.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FinanzasClient {

    @Autowired
    private RestClient restClient;

    public void registrarMovimientoBodega(String token, Long otId, Double monto) {
        try {
            Map<String, Object> movimiento = new HashMap<>();
            movimiento.put("tipo", "ingreso");
            movimiento.put("concepto", "Cobro bodegaje OT #" + otId);
            movimiento.put("monto", monto);
            movimiento.put("origen", "bodega");
            movimiento.put("otId", otId);
            movimiento.put("fecha", LocalDate.now().toString());

            restClient.post()
                    .uri("/api/v1/movimientos")
                    .header("Authorization", token)
                    .body(movimiento)
                    .retrieve()
                    .toBodilessEntity();

            log.info("Movimiento de bodegaje registrado en finanzas para OT {}", otId);
        } catch (Exception e) {
            log.error("Error al registrar movimiento de bodegaje en finanzas para OT {}: {}", otId, e.getMessage());
        }
    }
}
