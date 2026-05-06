package com.example.finanzas.exception;

public class GastoOperacionalNotFoundException extends RuntimeException {
    public GastoOperacionalNotFoundException(Long id) {
        super("Gasto operacional con id " + id + " no encontrado");
    }
}