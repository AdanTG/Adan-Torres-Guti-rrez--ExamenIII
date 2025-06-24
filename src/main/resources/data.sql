CREATE TABLE prestamos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    libro_id INT,
    nombre_usuario VARCHAR(100),
    fecha_prestamo DATE,
    fecha_devolucion DATE
);

INSERT INTO prestamos (libro_id, nombre_usuario, fecha_prestamo, fecha_devolucion) VALUES
(101, 'Juan Pérez', '2025-06-10', '2025-06-17'),
(102, 'María López', '2025-06-11', '2025-06-18'),
(103, 'Carlos Gómez', '2025-06-12', '2025-06-19'),
(104, 'Ana Torres', '2025-06-13', '2025-06-20'),
(105, 'Luis Ramírez', '2025-06-14', '2025-06-21');
