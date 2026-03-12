-- Crear la base de datos con el nombre exacto de tu properties
CREATE DATABASE IF NOT EXISTS ventapollo;
USE ventapollo;

-- 1. Crear el usuario
CREATE USER IF NOT EXISTS 'usuario_pollo'@'localhost' IDENTIFIED BY 'pollo123.';

-- 2. Darle todos los privilegios sobre la base de datos específica
GRANT ALL PRIVILEGES ON ventapollo.* TO 'usuario_pollo'@'localhost';

-- 3. Refrescar los privilegios para que los cambios apliquen
FLUSH PRIVILEGES;

-- Tabla de usuarios para login y registro
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL,
    imagen_url VARCHAR(255)
);

-- Tabla de pedidos
CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    estado VARCHAR(50) DEFAULT 'CONFIRMADO'
);

-- Tabla de detalles
CREATE TABLE IF NOT EXISTS detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    producto_id BIGINT,
    pedido_id BIGINT,
    CONSTRAINT fk_producto_det FOREIGN KEY (producto_id) REFERENCES producto(id),
    CONSTRAINT fk_pedido_det FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

-- Inserts de prueba para que puedas testear el carrito ya mismo
INSERT INTO producto (nombre, descripcion, precio, imagen_url) VALUES 
('Combo Alitas Picantes', '10 alitas + papas + bebida', 5500.0, 'https://img.freepik.com/vector-premium/alitas-pollo-frito-papas-fritas-ilustracion-estilo-pixel-art_1102-3850.jpg'),
('Combo Familiar Alitas', '12 piezas + papas + refresco', 7500.0, 'https://img.freepik.com/vector-premium/alitas-pollo-frito-papas-fritas-ilustracion-estilo-pixel-art_1102-3850.jpg'),
('Pollo Entero', 'El clásico pollo frito crujiente', 8900.0, 'https://img.freepik.com/vector-premium/pollo-frito-crujiente-cubo-pixel-art_1102-3832.jpg'),
('Pieza Individual', 'Muslo o Pechuga', 1200.0, 'https://img.freepik.com/vector-premium/pierna-pollo-frito-pixel-art_1102-3820.jpg');

UPDATE producto
SET imagen_url = 'https://images.unsplash.com/photo-1608039755401-742074f0548d'
WHERE id = 1;

UPDATE producto
SET imagen_url = 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58'
WHERE id = 2;

UPDATE producto
SET imagen_url = 'https://images.unsplash.com/photo-1513639776629-7b61b0ac49cb'
WHERE id = 3;

UPDATE producto
SET imagen_url = 'https://images.unsplash.com/photo-1562967914-608f82629710'
WHERE id = 4;

UPDATE producto SET nombre = 'Combo alitas picantes' WHERE id = 1;
UPDATE producto SET nombre = 'Combo familiar alitas' WHERE id = 2;
UPDATE producto SET nombre = 'Pollo entero' WHERE id = 3;
UPDATE producto SET nombre = 'Pieza individual' WHERE id = 4;
 o Pechuga', 1200.0, 'https://img.freepik.com/vector-premium/pierna-pollo-frito-pixel-art_1102-3820.jpg');
