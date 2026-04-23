package com.ventapollo.service;

import com.ventapollo.domain.Rol;
import com.ventapollo.domain.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UsuarioService {
    void guardar(Usuario usuario);
    void guardar(Usuario usuario, MultipartFile foto) throws IOException;
    void registrar(Usuario usuario, MultipartFile foto) throws IOException; // registro público → rol CLIENTE
    Usuario buscarPorCorreo(String correo);
    Usuario buscarPorId(Long id);
    boolean existeCorreo(String correo);
    Usuario autenticar(String correo, String password);
    List<Usuario> listarTodos();
    void asignarRol(Long usuarioId, Long rolId);
    List<Rol> listarRoles();
}
