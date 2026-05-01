package com.example.equipo.service;

import com.example.equipo.exception.EquipoNotFoundException;
import com.example.equipo.model.Equipo;
import com.example.equipo.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    //LISTAR TODOS LOS Equipos
    public List<Equipo> ListarEquipo(){
        return equipoRepository.findAll();
    }

    //OBTENER POR ID
    public Equipo obtenerPorId(Long id){
        return equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException(id));
    }


    public List<Equipo> findByClienteId(Long idCliente){
        return  equipoRepository.findByIdCliente(idCliente);
    }

    //Agregar usuarios
    public Equipo saveEquipo(Equipo equipo){
        return equipoRepository.save(equipo);
    }



    public  String deletedEquipo(Long id){
        equipoRepository.deleteById(id);
        return "Equipo " + id + " Eliminado";
    }

}
