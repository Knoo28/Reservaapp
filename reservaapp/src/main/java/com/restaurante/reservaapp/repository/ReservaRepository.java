package com.restaurante.reservaapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurante.reservaapp.model.Reserva;

public interface ReservaRepository extends JpaRepository <Reserva, Long>{
    List<Reserva> findByNombreClienteIgnoreCase(String nombreCliente);
    List<Reserva> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
}
