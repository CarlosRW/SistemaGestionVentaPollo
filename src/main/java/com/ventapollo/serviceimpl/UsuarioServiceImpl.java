package com.ventapollo.service.impl;

import com.ventapollo.dao.UsuarioDao;
import com.ventapollo.domain.Usuario;
import com.ventapollo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

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