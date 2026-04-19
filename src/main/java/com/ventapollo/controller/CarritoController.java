package com.ventapollo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ventapollo.domain.Item;
import com.ventapollo.domain.Producto;
import com.ventapollo.repository.ProductoRepository;
import com.ventapollo.service.ItemService;

@Controller
public class CarritoController {

    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ProductoRepository productoRepository;

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
    public String confirmar() {
        itemService.checkout();
        return "redirect:/producto/listado";
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