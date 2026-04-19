CREATE DATABASE IF NOT EXISTS ventapollo;
USE ventapollo;

CREATE USER IF NOT EXISTS 'usuario_pollo'@'localhost' IDENTIFIED BY 'pollo123.';
GRANT ALL PRIVILEGES ON ventapollo.* TO 'usuario_pollo'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL,
    imagen_url VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    estado VARCHAR(50) DEFAULT 'CONFIRMADO'
);

CREATE TABLE IF NOT EXISTS detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    producto_id BIGINT,
    pedido_id BIGINT,
    CONSTRAINT fk_producto_det FOREIGN KEY (producto_id) REFERENCES producto(id),
    CONSTRAINT fk_pedido_det FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

-- Insertamos los productos de Jose (Venta de Pollo)
INSERT INTO producto (nombre, descripcion, precio, imagen_url) VALUES 
('Combo Alitas Picantes', '10 alitas picantes + papas + bebida', 5500.0, 'https://images.unsplash.com/photo-1608039755401-742074f0548d?auto=format&fit=crop&w=800&q=80'),
('Combo Familiar Alitas', '12 piezas de alitas + papas + refresco grande', 7500.0, 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?auto=format&fit=crop&w=800&q=80'),
('Pollo Entero Crujiente', 'El clásico pollo frito crujiente de 8 piezas', 8900.0, 'https://images.unsplash.com/photo-1513639776629-7b61b0ac49cb?auto=format&fit=crop&w=800&q=80'),
('Pieza Individual', 'Una pieza de pollo (Muslo o Pechuga) a elegir', 1200.0, 'https://images.unsplash.com/photo-1562967914-608f82629710?auto=format&fit=crop&w=800&q=80'),
('Papas Supremas', 'Papas fritas con queso fundido y trozos de pollo', 2500.0, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?auto=format&fit=crop&w=800&q=80');

SELECT * FROM producto;