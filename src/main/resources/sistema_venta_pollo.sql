DROP DATABASE IF EXISTS ventapollo;
CREATE DATABASE ventapollo;
USE ventapollo;

CREATE TABLE rol (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    activa BOOLEAN DEFAULT TRUE
);

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    rol_id BIGINT,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (rol_id) REFERENCES rol(id)
);

CREATE TABLE producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DOUBLE NOT NULL,
    imagen_url VARCHAR(500),
    categoria_id BIGINT,
    CONSTRAINT fk_producto_cat FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE sucursal (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255)
);

CREATE TABLE metodo_pago (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    estado VARCHAR(50) DEFAULT 'CONFIRMADO',
    usuario_id BIGINT,
    metodo_pago_id BIGINT,
    CONSTRAINT fk_pedido_user FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_pedido_pago FOREIGN KEY (metodo_pago_id) REFERENCES metodo_pago(id)
);

CREATE TABLE detalle_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cantidad INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    producto_id BIGINT,
    pedido_id BIGINT,
    CONSTRAINT fk_producto_det FOREIGN KEY (producto_id) REFERENCES producto(id),
    CONSTRAINT fk_pedido_det FOREIGN KEY (pedido_id) REFERENCES pedido(id)
);

INSERT INTO rol (nombre) VALUES ('ADMIN'), ('CLIENTE');
INSERT INTO categoria (nombre) VALUES ('Combos'), ('Piezas Sueltas'), ('Acompañamientos');
INSERT INTO metodo_pago (tipo) VALUES ('Efectivo'), ('Tarjeta'), ('SINPE Móvil');
INSERT INTO sucursal (nombre, direccion) VALUES ('Alajuela', 'Cerca de la U'), ('San José', 'Centro'), ('Heredia', 'Parque Central');
INSERT INTO usuario (nombre, correo, password, rol_id) VALUES ('Jose Antonio', 'jose@mail.com', '123', 1);

INSERT INTO producto (nombre, descripcion, precio, imagen_url, categoria_id) VALUES 
('Combo Alitas Pro', '10 alitas picantes, papas grandes y refresco', 4500.0, 'https://images.unsplash.com/photo-1608039755401-742074f0548d?q=80&w=500', 1),
('Cubo Familiar', '12 piezas de pollo frito crujiente + acompañamiento', 9500.0, 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?q=80&w=500', 1),
('Sándwich de Pollo', 'Pechuga empanizada, lechuga, tomate y salsa especial', 3200.0, 'https://images.unsplash.com/photo-1626700051175-6818013e1d4f?q=80&w=500', 1),
('Combo alitas personales', '6 piezas de alitas con salsa barbacoa', 3800.0, 'https://images.unsplash.com/photo-1527477396000-e27163b481c2?q=80&w=500', 1),
('Combo Alitas Picantes', '10 alitas picantes + papas + bebida', 5500.0, 'https://images.unsplash.com/photo-1608039755401-742074f0548d?q=80&w=500', 1),
('Combo Familiar Alitas', '12 piezas de alitas + papas + refresco grande', 7500.0, 'https://images.unsplash.com/photo-1626645738196-c2a7c87a8f58?q=80&w=500', 1),
('Pollo Entero Crujiente', '8 piezas de pollo frito tradicional', 8900.0, 'https://images.unsplash.com/photo-1513639776629-7b61b0ac49cb?q=80&w=500', 2),
('Pieza Individual', 'Una pieza de pollo (Muslo o Pechuga) a elegir', 1200.0, 'https://images.unsplash.com/photo-1569058242252-62324e6ed73a?q=80&w=500', 2),
('Papas Supremas', 'Papas fritas con queso fundido y trozos de pollo', 2500.0, 'https://images.unsplash.com/photo-1573080496219-bb080dd4f877?q=80&w=500', 3);

INSERT INTO pedido (total, usuario_id, metodo_pago_id) VALUES (15820.0, 1, 2);
INSERT INTO detalle_pedido (cantidad, subtotal, producto_id, pedido_id) VALUES (1, 4500.0, 1, 1), (1, 9500.0, 2, 1);