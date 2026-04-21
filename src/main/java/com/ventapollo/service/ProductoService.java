package com.ventapollo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ventapollo.domain.Producto;
import com.ventapollo.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional
    public void eliminar(Long id) {
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