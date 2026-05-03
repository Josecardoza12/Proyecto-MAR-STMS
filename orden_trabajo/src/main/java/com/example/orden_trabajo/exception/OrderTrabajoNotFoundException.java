package com.example.orden_trabajo.exception;

public class OrderTrabajoNotFoundException extends RuntimeException{
    public OrderTrabajoNotFoundException(Long id){
        super("Order de trabajo con id " + id + " no encontrado");
    }

}
