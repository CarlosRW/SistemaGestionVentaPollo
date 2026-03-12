package com.ventapollo.controller;

import com.ventapollo.dao.ProductoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductoController {

    @Autowired
    private ProductoDao productoDao;

    @GetMapping({"/", "/menu"})
    public String verMenu(Model model) {
        var productos = productoDao.findAll();
        model.addAttribute("productos", productos);
        return "carrito/menu";
    }
}
