package com.ventapollo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name="detalle_pedido")
public class DetallePedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;
    private double subtotal;
    
    @ManyToOne
    @JoinColumn(name="producto_id")
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name="pedido_id")
    private Pedido pedido;

    public DetallePedido() {}
}