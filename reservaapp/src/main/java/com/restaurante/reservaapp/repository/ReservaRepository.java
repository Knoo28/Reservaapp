package com.restaurante.reservaapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurante.reservaapp.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    @Query("SELECT r FROM Reserva r ORDER BY r.fechaHora DESC")
    List<Reserva> listarTodas();

    @Query("SELECT r FROM Reserva r WHERE r.id = :id")
    Optional<Reserva> buscarPorId(@Param("id") Long id);

    @Query("SELECT r FROM Reserva r WHERE LOWER(r.nombreCliente) LIKE LOWER(CONCAT('%', :nombre, '%')) ORDER BY r.fechaHora DESC")
    List<Reserva> buscarPorNombre(@Param("nombre") String nombre);
}
