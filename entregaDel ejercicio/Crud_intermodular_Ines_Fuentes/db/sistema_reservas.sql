DROP DATABASE IF EXISTS sistema_reservas;
CREATE DATABASE sistema_reservas;
USE sistema_reservas;

CREATE TABLE USUARIO (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    fecha_nacimiento DATE,
    tipo_usuario ENUM('Administrador', 'Normal') NOT NULL
);

CREATE TABLE ADMINISTRADOR (
    id_usuario INT PRIMARY KEY,
    telefono_guardia VARCHAR(20) NOT NULL,
    CONSTRAINT fk_admin_usuario FOREIGN KEY (id_usuario) 
        REFERENCES USUARIO(id_usuario) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE USUARIONORMAL (
    id_usuario INT PRIMARY KEY,
    direccion VARCHAR(200),
    telefono_movil VARCHAR(20),
    fotografia VARCHAR(255),
    CONSTRAINT fk_usuarionormal_usuario FOREIGN KEY (id_usuario) 
        REFERENCES USUARIO(id_usuario) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);

CREATE TABLE RECURSO (
    id_recurso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    ubicacion VARCHAR(200),
    capacidad INT
);

CREATE TABLE HORARIO (
    id_horario INT AUTO_INCREMENT PRIMARY KEY,
    dia_semana ENUM('Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo') NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    CONSTRAINT chk_horario_horas CHECK (hora_fin > hora_inicio)
);

CREATE TABLE DISPONIBLEEN (
    id_recurso INT NOT NULL,
    id_horario INT NOT NULL,
    PRIMARY KEY (id_recurso, id_horario),
    CONSTRAINT fk_disponibleen_recurso FOREIGN KEY (id_recurso) 
        REFERENCES RECURSO(id_recurso) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
    CONSTRAINT fk_disponibleen_horario FOREIGN KEY (id_horario) 
        REFERENCES HORARIO(id_horario) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
);

CREATE TABLE RESERVA (
    id_recurso INT NOT NULL,
    id_reserva_local INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    coste DECIMAL(10,2),
    numero_plazas INT,
    motivo TEXT,
    observaciones TEXT,
    PRIMARY KEY (id_recurso, id_reserva_local),
    CONSTRAINT chk_reserva_horas CHECK (hora_fin > hora_inicio),
    CONSTRAINT fk_reserva_recurso FOREIGN KEY (id_recurso) 
        REFERENCES RECURSO(id_recurso) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE,
    CONSTRAINT fk_reserva_usuarionormal FOREIGN KEY (id_usuario) 
        REFERENCES USUARIONORMAL(id_usuario) 
        ON DELETE RESTRICT 
        ON UPDATE CASCADE
);

INSERT INTO USUARIO (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario) VALUES
('admin1@example.com', 'admin123', 'Inés Fuentes Martínez', '2003-01-08', 'Administrador'),
('admin2@example.com', 'admin456', 'Carlos García López', '1985-03-20', 'Administrador'),
('admin3@example.com', 'admin789', 'Laura Fernández Torres', '1988-09-30', 'Administrador'),
('admin4@example.com', 'admin012', 'Pedro Sánchez Ruiz', '1987-12-05', 'Administrador'),
('admin5@example.com', 'admin345', 'Sofía López Martín', '1991-06-18', 'Administrador'),
('usuario1@example.com', 'user123', 'María Rodríguez Sánchez', '1995-07-10', 'Normal'),
('usuario2@example.com', 'user456', 'Juan Pérez González', '1992-11-25', 'Normal'),
('usuario3@example.com', 'user789', 'Ana Martínez Fernández', '1998-02-14', 'Normal'),
('usuario4@example.com', 'user012', 'Luis Gómez Díaz', '1993-04-22', 'Normal'),
('usuario5@example.com', 'user345', 'Carmen Ruiz Jiménez', '1996-08-07', 'Normal');

INSERT INTO ADMINISTRADOR (id_usuario, telefono_guardia) VALUES
(1, '612345678'),
(2, '623456789'),
(3, '634567890'),
(4, '645678901'),
(5, '656789012');

INSERT INTO USUARIONORMAL (id_usuario, direccion, telefono_movil, fotografia) VALUES
(6, 'Calle Mayor 123, Madrid', '611111111', 'foto_maria.jpg'),
(7, 'Avenida Principal 45, Barcelona', '622222222', NULL),
(8, 'Plaza Central 67, Valencia', '633333333', 'foto_ana.jpg'),
(9, 'Calle Secundaria 89, Sevilla', '644444444', NULL),
(10, 'Calle Nueva 12, Bilbao', '655555555', 'foto_carmen.jpg');

INSERT INTO RECURSO (nombre, descripcion, ubicacion, capacidad) VALUES
('Sala de Conferencias A', 'Sala amplia con proyector y sistema de sonido', 'Planta 1, Ala Este', 50),
('Laboratorio de Informática', 'Laboratorio con 30 ordenadores', 'Planta 2, Ala Oeste', 30),
('Aula Magna', 'Aula grande para eventos', 'Planta 0, Entrada Principal', 200),
('Sala de Estudio', 'Sala silenciosa para estudio individual', 'Planta 3, Ala Norte', 40),
('Gimnasio', 'Instalación deportiva con equipamiento completo', 'Planta -1, Ala Sur', 25);

INSERT INTO HORARIO (dia_semana, hora_inicio, hora_fin) VALUES
('Lunes', '09:00:00', '12:00:00'),
('Martes', '14:00:00', '17:00:00'),
('Miércoles', '10:00:00', '13:00:00'),
('Jueves', '15:00:00', '18:00:00'),
('Viernes', '09:00:00', '11:00:00');

INSERT INTO DISPONIBLEEN (id_recurso, id_horario) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

INSERT INTO RESERVA (id_recurso, id_reserva_local, id_usuario, fecha, hora_inicio, hora_fin, coste, numero_plazas, motivo, observaciones) VALUES
(1, 1, 6, '2026-01-15', '09:00:00', '11:00:00', 50.00, 20, 'Reunión de equipo', 'Necesitamos proyector'),
(2, 1, 6, '2026-01-16', '14:00:00', '16:00:00', 30.00, 15, 'Clase práctica', NULL),
(3, 1, 7, '2026-01-17', '10:00:00', '12:00:00', 100.00, 50, 'Conferencia', 'Evento importante'),
(1, 2, 8, '2026-01-18', '09:30:00', '10:30:00', 25.00, 10, 'Presentación', 'Sala pequeña suficiente'),
(4, 1, 6, '2026-01-19', '15:00:00', '17:00:00', NULL, 1, 'Estudio individual', 'Necesito silencio');