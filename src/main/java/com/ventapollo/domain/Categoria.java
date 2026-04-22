package com.ventapollo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="categoria")
public class Categoria implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String nombre;
    private boolean activa;

    public Categoria() {
    }

    public Categoria(String nombre, boolean activa) {
        this.nombre = nombre;
        this.activa = activa;
    }
}