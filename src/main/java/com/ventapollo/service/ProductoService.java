/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ventapollo.service; 

import com.ventapollo.Producto; 
import java.util.ArrayList;

public class ProductoService {
    
   
    private ArrayList<Producto> listaProductos;

    public ProductoService() {
        this.listaProductos = new ArrayList<>();
        
        
        listaProductos.add(new Producto(1, "Combo Familiar", "8 piezas + papas + refresco", 12500, "Combos"));
        listaProductos.add(new Producto(2, "Pieza Muslo", "Pollo crujiente", 1500, "Individual"));
    }

    
    public void agregar(Producto p) {
        listaProductos.add(p);
    }

    // --- PUNTO PA-9: ELIMINAR PRODUCTO ---
    public void eliminar(int id) {
        
        listaProductos.removeIf(p -> p.getId() == id);
    }

    
    public void actualizarPrecio(int id, double nuevoPrecio) {
        for (Producto p : listaProductos) {
            if (p.getId() == id) {
                p.setPrecio(nuevoPrecio); // Cambia el precio usando el setter que hicimos
                break;
            }
        }
    }

   
    public ArrayList<Producto> obtenerTodos() {
        return listaProductos;
    }
}