package com.ventapollo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Item extends Producto {
    private int cantidad; 

    public Item() {}

    public Item(Producto producto) {
        super.setId(producto.getId());
        super.setNombre(producto.getNombre());
        super.setPrecio(producto.getPrecio());
        super.setImagenUrl(producto.getImagenUrl());
        this.cantidad = 1; // Cambia de 0 a 1 para que al agregar ya cuente uno
    }
}