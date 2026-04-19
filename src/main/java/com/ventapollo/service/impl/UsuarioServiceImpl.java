package com.ventapollo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ventapollo.domain.Usuario;
import com.ventapollo.repository.UsuarioRepository;
import com.ventapollo.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioDao;

    @Override
    public void guardar(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    public Usuario buscarPorCorreo(String correo) {
        return usuarioDao.findByCorreo(correo);
    }

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioDao.existsByCorreo(correo);
    }

    @Override
    public Usuario autenticar(String correo, String password) {
        Usuario usuario = usuarioDao.findByCorreo(correo);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}