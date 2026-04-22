package com.ventapollo.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.ventapollo.domain.Producto;
import com.ventapollo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    /**
     * Guarda un producto. Si se adjuntó imagen, la sube a Firebase y usa esa URL.
     * Si no, conserva la URL anterior (edición) o la del campo de texto.
     */
    @Transactional
    public void guardar(Producto producto, MultipartFile imagen) throws IOException {
        boolean tieneImagenNueva = imagen != null && !imagen.isEmpty();

        if (tieneImagenNueva) {
            // Si ya tenía imagen en Firebase, la eliminamos antes de subir la nueva
            if (producto.getId() != null) {
                Producto anterior = productoRepository.findById(producto.getId()).orElse(null);
                if (anterior != null && anterior.getImagenUrl() != null
                        && anterior.getImagenUrl().contains("firebasestorage")) {
                    firebaseStorageService.eliminarImagen(anterior.getImagenUrl());
                }
            }
            String url = firebaseStorageService.subirImagen(imagen, "productos");
            producto.setImagenUrl(url);
        } else if (producto.getId() != null) {
            // Edición sin nueva imagen: conserva la URL actual
            Producto anterior = productoRepository.findById(producto.getId()).orElse(null);
            if (anterior != null && (producto.getImagenUrl() == null || producto.getImagenUrl().isBlank())) {
                producto.setImagenUrl(anterior.getImagenUrl());
            }
        }

        productoRepository.save(producto);
    }

    /** Versión sin imagen (mantiene compatibilidad) */
    @Transactional
    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto != null && producto.getImagenUrl() != null
                && producto.getImagenUrl().contains("firebasestorage")) {
            firebaseStorageService.eliminarImagen(producto.getImagenUrl());
        }
        productoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarPorRangoPrecio(double precioMin, double precioMax) {
        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioMin, precioMax);
    }

    @Transactional(readOnly = true)
    public List<Producto> listarOrdenadosPorPrecio() {
        return productoRepository.listarOrdenadosPorPrecio();
    }

    @Transactional(readOnly = true)
    public List<Producto> consultaPrecioSQL(double precioMin, double precioMax) {
        return productoRepository.consultaPrecioSQL(precioMin, precioMax);
    }
}
