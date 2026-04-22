package com.ventapollo.controller;

import com.ventapollo.domain.Usuario;
import com.ventapollo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registrarUsuario(
            @ModelAttribute Usuario usuario,
            @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
            Model model) {

        if (usuarioService.existeCorreo(usuario.getCorreo())) {
            model.addAttribute("error", "Ya existe un usuario con ese correo.");
            model.addAttribute("usuario", usuario);
            return "usuario/registro";
        }

        try {
            usuarioService.guardar(usuario, fotoFile);
            model.addAttribute("mensaje", "¡Registro exitoso! Ya podés iniciar sesión.");
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            return "usuario/registro";
        }

        return "usuario/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String correo,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Usuario usuario = usuarioService.autenticar(correo, password);

        if (usuario != null) {
            // Guarda el usuario en sesión para usarlo en toda la app
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/producto/listado";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos.");
            return "usuario/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }
}
