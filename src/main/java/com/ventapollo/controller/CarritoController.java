package com.ventapollo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

import com.ventapollo.domain.DetallePedido;
import com.ventapollo.domain.Item;
import com.ventapollo.domain.Producto;
import com.ventapollo.domain.Pedido;
import com.ventapollo.domain.Usuario;
import com.ventapollo.repository.DetallePedidoRepository;
import com.ventapollo.repository.ProductoRepository;
import com.ventapollo.repository.PedidoRepository;
import com.ventapollo.service.ItemService;

@Controller
public class CarritoController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @GetMapping("/carrito/listado")
    public String inicio(Model model) {
        var items = itemService.gets();
        var carritoTotal = 0.0;
        for (Item i : items) {
            carritoTotal += (i.getPrecio() * i.getCantidad());
        }
        model.addAttribute("items", items);
        model.addAttribute("carritoTotal", carritoTotal);
        return "/carrito/listado";
    }

    @GetMapping("/carrito/agregar/{id}")
    public String agregarItem(@PathVariable("id") Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null) {
            itemService.save(new Item(producto));
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarItem(@PathVariable("id") Long id) {
        Item item = new Item();
        item.setId(id);
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    @GetMapping("/carrito/confirmar")
    public String confirmar(
            // ✅ FIX: recibe los parámetros del formulario correctamente
            @RequestParam(value = "metodoPagoId", defaultValue = "1") Long metodoPagoId,
            @RequestParam(value = "sucursalId", defaultValue = "1") Long sucursalId,
            HttpSession session) {

        // ✅ FIX: obtiene el usuario real desde la sesión, no hardcodeado
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/usuario/login";
        }

        var items = itemService.gets();

        if (items != null && !items.isEmpty()) {
            var carritoTotal = 0.0;
            for (Item i : items) {
                carritoTotal += (i.getPrecio() * i.getCantidad());
            }

            // Guarda el pedido con el usuario real y método de pago correcto
            Pedido pedido = new Pedido();
            pedido.setTotal(carritoTotal);
            pedido.setEstado("CONFIRMADO");
            pedido.setUsuarioId(usuarioLogueado.getId()); // ✅ FIX: ID real del usuario
            pedido.setMetodoPagoId(metodoPagoId);         // ✅ FIX: método de pago real
            pedido = pedidoRepository.save(pedido);

            // ✅ FIX: guarda cada DetallePedido para que la factura tenga contenido
            for (Item i : items) {
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(i.getCantidad());
                detalle.setSubtotal(i.getPrecio() * i.getCantidad());

                Producto producto = new Producto();
                producto.setId(i.getId());
                detalle.setProducto(producto);
                detalle.setPedido(pedido);

                detallePedidoRepository.save(detalle);
            }

            itemService.checkout();
        }

        return "redirect:/factura/mis-pedidos";
    }

    @GetMapping("/carrito/sumar/{id}")
    public String sumarItem(@PathVariable("id") Long id) {
        var items = itemService.gets();
        for (Item i : items) {
            if (i.getId().equals(id)) {
                i.setCantidad(i.getCantidad() + 1);
                break;
            }
        }
        return "redirect:/carrito/listado";
    }

    @GetMapping("/carrito/restar/{id}")
    public String restarItem(@PathVariable("id") Long id) {
        var items = itemService.gets();
        for (Item i : items) {
            if (i.getId().equals(id) && i.getCantidad() > 1) {
                i.setCantidad(i.getCantidad() - 1);
                break;
            }
        }
        return "redirect:/carrito/listado";
    }
}