package com.ventapollo.service.impl;

import com.ventapollo.domain.Rol;
import com.ventapollo.domain.Usuario;
import com.ventapollo.repository.RolRepository;
import com.ventapollo.repository.UsuarioRepository;
import com.ventapollo.service.FirebaseStorageService;
import com.ventapollo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioDao;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Override
    public void guardar(Usuario usuario) {
        usuarioDao.save(usuario);
    }

    @Override
    public void guardar(Usuario usuario, MultipartFile foto) throws IOException {
        if (foto != null && !foto.isEmpty()) {
            String url = firebaseStorageService.subirImagen(foto, "perfiles");
            usuario.setFotoPerfilUrl(url);
        }
        usuarioDao.save(usuario);
    }

    /**
     * Registro público: siempre asigna rol CLIENTE (id=2).
     */
    @Override
    public void registrar(Usuario usuario, MultipartFile foto) throws IOException {
        Rol rolCliente = rolRepository.findByNombre("CLIENTE");
        if (rolCliente == null) rolCliente = rolRepository.findById(2L).orElseThrow();
        usuario.setRol(rolCliente);

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
    public Usuario buscarPorId(Long id) {
        return usuarioDao.findById(id).orElse(null);
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

    @Override
    public List<Usuario> listarTodos() {
        return usuarioDao.findAll();
    }

    @Override
    public void asignarRol(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioDao.findById(usuarioId).orElseThrow();
        Rol rol = rolRepository.findById(rolId).orElseThrow();
        usuario.setRol(rol);
        usuarioDao.save(usuario);
    }

    @Override
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }
}
