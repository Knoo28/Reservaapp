package com.restaurante.reservaapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.restaurante.reservaapp.model.Reserva;
import com.restaurante.reservaapp.service.ReservaService;

@Controller
@RequestMapping("/reservas")
public class ReservaWebController {
    @Autowired
    private ReservaService reservaService;

    // Ver todas las reservas
    @GetMapping
    public String verReservas(Model model) {
        model.addAttribute("reservas", reservaService.obtenerTodas());
        model.addAttribute("reservaNueva", new Reserva()); // form nueva reserva
        return "reservas"; // plantilla Thymeleaf: templates/reservas.html
    }

    // Guardar nueva reserva
    @PostMapping
    public String guardarReserva(@ModelAttribute Reserva reserva) {
        reservaService.guardar(reserva);
        return "redirect:/reservas"; // redirigir para evitar reenvío del form
    }

    // Eliminar reserva
    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
        return "redirect:/reservas"; // redirigir a la lista de reservas
    }

    // Editar reserva
    @PostMapping("/editar")
    public String editarReserva(@ModelAttribute Reserva reserva) {
        reservaService.actualizar(reserva);
        return "redirect:/reservas"; // redirigir después de editar
    }

    // Buscar reservas por nombre
    @GetMapping("/buscar")
    public String buscarPorNombre(@RequestParam(required = false) String nombre, Model model) {
        if (nombre != null && !nombre.isEmpty()) {
            model.addAttribute("reservas", reservaService.buscarPorNombre(nombre));
        } else {
            model.addAttribute("reservas", reservaService.obtenerTodas());
        }
        return "reservas"; // cargar vista de reservas con resultados de búsqueda
    }

    // Buscar reserva por ID
    @GetMapping("/buscarId")
    public String buscarPorId(@RequestParam(required = false) Long id, Model model) {
        if (id != null) {
            Optional<Reserva> reserva = reservaService.buscarPorId(id);
            if (reserva.isPresent()) {
                model.addAttribute("reservas", List.of(reserva.get()));
            } else {
                model.addAttribute("message", "Reserva no encontrada");
                model.addAttribute("reservas", reservaService.obtenerTodas());
            }
        } else {
            model.addAttribute("reservas", reservaService.obtenerTodas());
        }

        return "reservas"; // Vista de reservas con el resultado de la búsqueda
    }

}