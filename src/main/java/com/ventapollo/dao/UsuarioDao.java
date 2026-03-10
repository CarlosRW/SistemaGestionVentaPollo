package com.ventapollo.dao;

import com.ventapollo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioDao extends JpaRepository<Usuario, Long> {
    Usuario findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}