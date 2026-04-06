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

    // PUNTO 1: Mostrar la página web con los pollos
    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.obtenerTodos();
        model.addAttribute("productos", productos);
        
        // Retornamos la ruta: carpeta producto / archivo listado
        return "producto/listado"; 
    }

    // Método de prueba para agregar uno rápido
    @GetMapping("/test-agregar")
    public String testAgregar() {
        Producto p = new Producto();
        p.setNombre("Combo Alita Pro");
        p.setDescripcion("6 alitas + papas");
        p.setPrecio(4500.0);
        productoService.agregar(p);
        return "redirect:/producto/listado"; 
    }
}