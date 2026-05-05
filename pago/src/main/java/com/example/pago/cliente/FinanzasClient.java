package com.example.pago.cliente;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class FinanzasClient {

    @Autowired
    private WebClient finanzasWebClient;

    public void registrarMovimiento(String token, Long otId, Double monto, String concepto) {
        try {
            Map<String, Object> movimiento = new HashMap<>();
            movimiento.put("tipo", "ingreso");
            movimiento.put("concepto", concepto);
            movimiento.put("monto", monto);
            movimiento.put("origen", "pago");
            movimiento.put("otId", otId);
            movimiento.put("fecha", LocalDate.now().toString());

            finanzasWebClient.post()
                    .uri("/api/v1/movimientos")
                    .header("Authorization", token)
                    .bodyValue(movimiento)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe(
                            response -> log.info("Movimiento registrado en finanzas para OT {}", otId),
                            error -> log.error("Error al registrar movimiento en finanzas: {}", error.getMessage())
                    );
        } catch (Exception e) {
            log.error("Error al comunicarse con finanzas: {}", e.getMessage());
        }
    }
}
