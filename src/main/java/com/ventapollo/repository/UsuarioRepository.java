package com.ventapollo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ventapollo.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}