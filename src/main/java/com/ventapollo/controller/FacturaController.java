package com.ventapollo.controller;

import com.ventapollo.domain.DetallePedido;
import com.ventapollo.domain.Pedido;
import com.ventapollo.domain.Usuario;
import com.ventapollo.repository.DetallePedidoRepository;
import com.ventapollo.repository.PedidoRepository;
import com.ventapollo.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/factura")
public class FacturaController {

    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;
    @Autowired private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public String verFactura(@PathVariable Long id, HttpSession session, Model model) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) return "redirect:/producto/listado";

        List<DetallePedido> detalles = detallePedidoRepository.findByPedidoId(id);
        Usuario cliente = usuarioService.buscarPorId(pedido.getUsuarioId());
        Usuario logueado = (Usuario) session.getAttribute("usuarioLogueado");

        double subtotal = pedido.getTotal() / 1.13;
        double iva = pedido.getTotal() - subtotal;

        model.addAttribute("pedido", pedido);
        model.addAttribute("detalles", detalles);
        model.addAttribute("cliente", cliente);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("iva", iva);

        return "factura/factura";
    }

    /** Historial de compras del usuario logueado */
    @GetMapping("/mis-pedidos")
    public String misPedidos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return "redirect:/usuario/login";

        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdOrderByFechaPedidoDesc(usuario.getId());
        model.addAttribute("pedidos", pedidos);
        return "factura/mis-pedidos";
    }
}
