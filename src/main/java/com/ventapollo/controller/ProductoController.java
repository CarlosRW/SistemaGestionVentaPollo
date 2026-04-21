package com.ventapollo.controller;

import com.ventapollo.domain.Producto;
import com.ventapollo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.obtenerTodos();
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto) {
        productoService.guardar(producto);
        return "redirect:/producto/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        return "producto/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id) {
        productoService.eliminar(id);
        return "redirect:/producto/listado";
    }

    @GetMapping("/buscar")
    public String buscarPorNombre(@RequestParam("nombre") String nombre, Model model) {
        var productos = productoService.buscarPorNombre(nombre);
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    @GetMapping("/precio")
    public String buscarPorPrecio(@RequestParam("min") double min,
                                  @RequestParam("max") double max,
                                  Model model) {
        var productos = productoService.buscarPorRangoPrecio(min, max);
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    @GetMapping("/ordenados")
    public String listarOrdenados(Model model) {
        var productos = productoService.listarOrdenadosPorPrecio();
        model.addAttribute("productos", productos);
        return "producto/listado";
    }

    @GetMapping("/sql")
    public String consultaSQL(@RequestParam("min") double min,
                              @RequestParam("max") double max,
                              Model model) {
        var productos = productoService.consultaPrecioSQL(min, max);
        model.addAttribute("productos", productos);
        return "producto/listado";
    }
}