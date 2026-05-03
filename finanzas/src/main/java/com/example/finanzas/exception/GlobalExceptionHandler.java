package com.example.finanzas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovimientoCajaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMovimientoNotFound(MovimientoCajaNotFoundException ex) {
        HashMap<String, String> errores = new HashMap<>();
        errores.put("id", ex.getMessage());
        return new ErrorResponse(404, "Movimiento de caja no encontrado", errores);
    }

    @ExceptionHandler(GastoOperacionalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGastoNotFound(GastoOperacionalNotFoundException ex) {
        HashMap<String, String> errores = new HashMap<>();
        errores.put("id", ex.getMessage());
        return new ErrorResponse(404, "Gasto operacional no encontrado", errores);
    }
}
