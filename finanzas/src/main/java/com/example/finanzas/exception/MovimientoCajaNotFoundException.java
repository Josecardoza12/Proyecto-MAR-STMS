package com.example.finanzas.exception;

public class MovimientoCajaNotFoundException extends RuntimeException {
    public MovimientoCajaNotFoundException(Long id) {
        super("Movimiento de caja con id " + id + " no encontrado");
    }
}
