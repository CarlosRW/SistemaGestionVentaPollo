package com.ventapollo.controller;

import com.ventapollo.domain.Usuario;
import com.ventapollo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "usuario/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "Ya existe un usuario con ese correo");
            model.addAttribute("usuario", usuario);
            return "usuario/registro";
        }

        usuarioService.guardar(usuario);
        model.addAttribute("mensaje", "Usuario registrado correctamente");
        return "usuario/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String correo,
                        @RequestParam String password,
                        Model model) {

        Usuario usuario = usuarioService.autenticar(correo, password);

        if (usuario != null) {
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "usuario/login";
        }
    }
}