package com.restaurante.reservaapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restaurante.reservaapp.model.Reserva;
import com.restaurante.reservaapp.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaApiController {
    @Autowired
    private ReservaService reservaService;

    // API RESTful: para pruebas con Postman
    @GetMapping
    @ResponseBody
    public List<Reserva> obtenerReservasApi() {
        return reservaService.obtenerTodas();
    }

    @PostMapping
    @ResponseBody
    public Reserva crearReservaApi(@RequestBody Reserva reserva) {
        return reservaService.guardar(reserva);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
    }

    // actualizar reserva
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva nuevaReserva) {
        Optional<Reserva> reservaExistente = reservaService.buscarPorId(id);
        if (reservaExistente.isPresent()) {
            Reserva reserva = reservaExistente.get();
            reserva.setNombreCliente(nuevaReserva.getNombreCliente());
            reserva.setCantidadPersonas(nuevaReserva.getCantidadPersonas());
            reserva.setFechaHora(nuevaReserva.getFechaHora());
            reservaService.guardar(reserva);
            return ResponseEntity.ok(reserva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // buscar por id
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.buscarPorId(id);
        return reserva.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/nombre/{nombre}")
    @ResponseBody
    public List<Reserva> buscarPorNombre(@PathVariable String nombre) {
        return reservaService.buscarPorNombre(nombre);
    }
}