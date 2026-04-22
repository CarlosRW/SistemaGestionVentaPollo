package com.ventapollo.service;

import com.ventapollo.domain.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UsuarioService {
    void guardar(Usuario usuario);
    void guardar(Usuario usuario, MultipartFile foto) throws IOException;
    Usuario buscarPorCorreo(String correo);
    boolean existeCorreo(String correo);
    Usuario autenticar(String correo, String password);
}
