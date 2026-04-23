package com.ventapollo.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String password;

    @Column(name = "foto_perfil_url")
    private String fotoPerfilUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    // Helpers de conveniencia
    public boolean esAdmin() {
        return rol != null && "ADMIN".equalsIgnoreCase(rol.getNombre());
    }

    public boolean esCliente() {
        return rol != null && "CLIENTE".equalsIgnoreCase(rol.getNombre());
    }
}
