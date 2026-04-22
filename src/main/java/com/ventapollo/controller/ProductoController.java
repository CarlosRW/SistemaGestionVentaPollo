package com.ventapollo.controller;

import com.ventapollo.domain.Producto;
import com.ventapollo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("productos", productoService.obtenerTodos());
        return "producto/listado";
    }

    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto/formulario";
    }

    @PostMapping("/guardar")
    public String guardarProducto(
            @ModelAttribute Producto producto,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            RedirectAttributes redirectAttributes) {

        try {
            productoService.guardar(producto, imagenFile);
            redirectAttributes.addFlashAttribute("exito", "Producto guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al guardar el producto: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = productoService.obtenerPorId(id);
        model.addAttribute("producto", producto);
        return "producto/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            productoService.eliminar(id);
            redirectAttributes.addFlashAttribute("exito", "Producto eliminado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo eliminar: " + e.getMessage());
        }
        return "redirect:/producto/listado";
    }

    @GetMapping("/buscar")
    public String buscarPorNombre(@RequestParam("nombre") String nombre, Model model) {
        model.addAttribute("productos", productoService.buscarPorNombre(nombre));
        return "producto/listado";
    }

    @GetMapping("/precio")
    public String buscarPorPrecio(@RequestParam("min") double min,
                                  @RequestParam("max") double max,
                                  Model model) {
        model.addAttribute("productos", productoService.buscarPorRangoPrecio(min, max));
        return "producto/listado";
    }

    @GetMapping("/ordenados")
    public String listarOrdenados(Model model) {
        model.addAttribute("productos", productoService.listarOrdenadosPorPrecio());
        return "producto/listado";
    }

    @GetMapping("/sql")
    public String consultaSQL(@RequestParam("min") double min,
                              @RequestParam("max") double max,
                              Model model) {
        model.addAttribute("productos", productoService.consultaPrecioSQL(min, max));
        return "producto/listado";
    }
}
