/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventapollo.service; 

import com.ventapollo.domain.Producto; 
import com.ventapollo.dao.ProductoDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoDao productoDao; 

    // Obtener la lista completa desde MySQL
    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoDao.findAll();
    }

    // --- PUNTO PA-9: AGREGAR / GUARDAR PRODUCTO ---
    @Transactional
    public void agregar(Producto p) {
        // En JPA, .save() inserta si el ID es nulo o nuevo
        productoDao.save(p);
    }

    // Método para eliminar por ID
    @Transactional
    public void eliminar(Long id) {
        productoDao.deleteById(id);
    }

    // --- PUNTO PA-10: ACTUALIZAR PRECIO ---
    @Transactional
    public void actualizarPrecio(Long id, double nuevoPrecio) {
        // Buscamos el producto en la base de datos
        Producto p = productoDao.findById(id).orElse(null);
        
        if (p != null) {
            p.setPrecio(nuevoPrecio);
            // Al hacer save de un objeto que ya tiene ID, JPA hace un UPDATE
            productoDao.save(p); 
        }
    }
}