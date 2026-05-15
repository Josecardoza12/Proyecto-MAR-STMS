package com.example.pago.cliente;

import com.example.pago.model.Finanzas;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class FinanzasClient {

    @Autowired
    @Qualifier("FinanzasWebClient")
    private WebClient webClient;

    public Mono<Void> registrarMovimiento(String token, Long otId, Double monto, String detalle) {
        log.info("Notificando a finanzas - OT: {}, monto: {}", otId, monto);
        Finanzas finanzas = new Finanzas();
        finanzas.setCategoria("ingreso");
        finanzas.setDetalle(detalle);
        finanzas.setTotal(monto);
        finanzas.setOtId(otId);
        finanzas.setMedioPago("sistema");

        return webClient.post()
                .uri("")
                .header("Authorization", token)
                .bodyValue(finanzas)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new ResponseStatusException(
                                HttpStatus.BAD_REQUEST, "Error al registrar en finanzas")))
                .bodyToMono(Void.class);
    }
}
