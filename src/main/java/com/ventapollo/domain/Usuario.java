package com.ventapollo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String correo;
    private String password;

    @Column(name = "foto_perfil_url")
    private String fotoPerfilUrl;
}
