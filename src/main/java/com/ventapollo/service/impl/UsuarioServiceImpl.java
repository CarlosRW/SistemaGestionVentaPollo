package com.ventapollo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ventapollo.domain.Usuario;
import com.ventapollo.repository.UsuarioRepository;
import com.ventapollo.service.FirebaseStorageService;
import com.ventapollo.service.UsuarioService;

import java.io.IOException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioDao;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Override
    public void guardar(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    /**
     * Registra un usuario. Si se adjuntó foto de perfil, la sube a Firebase
     * y guarda la URL en el campo fotoPerfilUrl.
     */
    @Override
    public void guardar(Usuario usuario, MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            String url = firebaseStorageService.subirImagen(foto, "perfiles");
            usuario.setFotoPerfilUrl(url);
        }
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
