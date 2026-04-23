package com.ventapollo.controller;

import com.ventapollo.domain.Usuario;
import com.ventapollo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    /** Verifica que quien accede sea ADMIN; si no, redirige. */
    private boolean noEsAdmin(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        return u == null || !u.esAdmin();
    }

    @GetMapping("/panel")
    public String panel(HttpSession session, Model model) {
        if (noEsAdmin(session)) return "redirect:/usuario/login";
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("roles", usuarioService.listarRoles());
        return "admin/panel";
    }

    @PostMapping("/asignar-rol")
    public String asignarRol(@RequestParam Long usuarioId,
                             @RequestParam Long rolId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (noEsAdmin(session)) return "redirect:/usuario/login";
        try {
            usuarioService.asignarRol(usuarioId, rolId);
            redirectAttributes.addFlashAttribute("exito", "Rol actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar el rol: " + e.getMessage());
        }
        return "redirect:/admin/panel";
    }
}
