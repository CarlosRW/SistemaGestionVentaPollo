package com.ventapollo.service;

import com.ventapollo.domain.Usuario;

public interface UsuarioService {
    void guardar(Usuario usuario);
    Usuario buscarPorCorreo(String correo);
    boolean existeCorreo(String correo);
    Usuario autenticar(String correo, String password);
}