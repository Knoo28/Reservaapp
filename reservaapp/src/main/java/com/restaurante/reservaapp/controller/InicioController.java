package com.restaurante.reservaapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    @GetMapping("/")
    public String verInicio() {
        return "index"; // carga index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // carga login.html
    }
}