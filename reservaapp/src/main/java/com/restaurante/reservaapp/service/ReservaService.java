package com.restaurante.reservaapp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurante.reservaapp.model.Reserva;
import com.restaurante.reservaapp.repository.ReservaRepository;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    
    public List<Reserva> listarTodas(){
        return reservaRepository.findAll();
    }

    public Reserva guardar(Reserva reserva){
        return reservaRepository.save(reserva);
    }

    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public boolean eliminar (Long id){
        if (reservaRepository.existsById(id)){
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Reserva actualizar (Long id, Reserva reservaActualizada){
        return reservaRepository.findById(id).map(r ->{
            r.setNombreCliente(reservaActualizada.getNombreCliente());
            r.setCantidadPersonas(reservaActualizada.getCantidadPersonas());
            r.setFechaHora(reservaActualizada.getFechaHora());
            return reservaRepository.save(r);
        }).orElse(null);
    }

    public List <Reserva> buscarPorNombre(String nombre){
        return reservaRepository.findByNombreClienteIgnoreCase(nombre);
    }
    
    public List<Reserva> buscarPorFecha(LocalDate fecha){
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59);
        return reservaRepository.findByFechaHoraBetween(inicio, fin);
    }
}
