package com.restaurante.reservaapp.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.restaurante.reservaapp.model.Reserva;
import com.restaurante.reservaapp.service.ReservaService;

@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/")
    public String verInicio() {
        return "index";
    }

    @GetMapping("/reservas")
    public String verReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        return "reservas";
    }

    @PostMapping("/reservas")
    public String guardarReserva(@RequestParam String nombreCliente,
            @RequestParam int cantidadPersonas,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaHora) {
        Reserva reserva = new Reserva();
        reserva.setNombreCliente(nombreCliente);
        reserva.setCantidadPersonas(cantidadPersonas);
        reserva.setFechaHora(fechaHora);
        reservaService.guardar(reserva);
        return "redirect:/reservas";
    }

    // API RESTful: para pruebas con herramientas como Postman
    @GetMapping("/api/reservas")
    @ResponseBody
    public List<Reserva> obtenerReservasApi() {
        return reservaService.listarTodas();
    }

    @PostMapping("/api/reservas")
    @ResponseBody
    public Reserva crearReservaApi(@RequestBody Reserva reserva) {
        return reservaService.guardar(reserva);
    }

    @DeleteMapping("/api/reservas/{id}")
    @ResponseBody
    public void eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
    }

    @PutMapping("/api/reservas/{id}")
    @ResponseBody
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva nuevaReserva) {
        Optional<Reserva> reservaExistente = Optional.empty();
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

    @GetMapping("/api/reservas/nombre/{nombre}")
    @ResponseBody

    public List<Reserva> buscarPorNombre(@PathVariable String nombre) {
        return reservaService.buscarPorNombre(nombre);
    }

    @GetMapping("/api/reservas/fecha")
    @ResponseBody
    public List<Reserva> buscarPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return reservaService.buscarPorFecha(fecha);
    }
}
