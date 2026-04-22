package com.ventapollo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="pedido")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double total;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_pedido", insertable = false, updatable = false)
    private Date fechaPedido;
    
    private String estado;

    @Column(name="usuario_id")
    private Long usuarioId;

    @Column(name="metodo_pago_id")
    private Long metodoPagoId;
    
    public Pedido() {}
}