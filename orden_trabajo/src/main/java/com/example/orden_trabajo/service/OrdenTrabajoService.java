package com.example.orden_trabajo.service;

import com.example.orden_trabajo.exception.OrderTrabajoNotFoundException;
import com.example.orden_trabajo.model.OrdenTrabajo;
import com.example.orden_trabajo.repository.OrdenTrabajoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenTrabajoService {
    @Autowired
    private OrdenTrabajoRepository ordenTrabajoRepository;

    //LISTAR TODOS LOS Equipos
    public List<OrdenTrabajo> ListarOt(){
        return ordenTrabajoRepository.findAll();
    }

    //OBTENER POR ID
    public OrdenTrabajo obtenerOtPorId(Long id){
        return ordenTrabajoRepository.findById(id)
                .orElseThrow(() -> new OrderTrabajoNotFoundException(id));
    }


    //Obtener por id de clientes
    public List<OrdenTrabajo> findByClienteId(Long idCliente){
        return  ordenTrabajoRepository.findByIdCliente(idCliente);
    }

    //Obtener por id de Equipos
    public List<OrdenTrabajo> findByIdEquipo(Long IdEquipo){
        return  ordenTrabajoRepository.findByIdEquipo(IdEquipo);
    }

    //Agregar ot
    public OrdenTrabajo saveOt(OrdenTrabajo ot){
        return ordenTrabajoRepository.save(ot);
    }


    //eliminar ot
    public  String deletedOt(Long id){
        ordenTrabajoRepository.deleteById(id);
        return "Orden de trabajo con " + id + " Eliminado";
    }

}
