package com.ventapollo.repository;

import com.ventapollo.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Consulta derivada: buscar por nombre
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Consulta derivada: buscar por rango de precio y ordenar
    List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioMin, double precioMax);

    // JPQL
    @Query("SELECT p FROM Producto p ORDER BY p.precio ASC")
    List<Producto> listarOrdenadosPorPrecio();

    // SQL nativo
    @Query(value = "SELECT * FROM producto WHERE precio >= ?1 AND precio <= ?2 ORDER BY precio ASC", nativeQuery = true)
    List<Producto> consultaPrecioSQL(double precioMin, double precioMax);
}