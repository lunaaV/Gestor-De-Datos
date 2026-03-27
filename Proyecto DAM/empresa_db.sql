-- Base de datos para MySQL Empresa
DROP DATABASE IF EXISTS empresa_db;
CREATE DATABASE empresa_db CHARACTER SET utf8mb4;
USE empresa_db;

CREATE TABLE roles (
 id_rol INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre_rol VARCHAR(50) NOT NULL,
 descripcion VARCHAR(200)
);

CREATE TABLE usuarios (
 id_usuario INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100) NOT NULL,
 email VARCHAR(150) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 rol INT UNSIGNED,
 fecha_creacion DATE,
 activo BOOLEAN DEFAULT TRUE,
 FOREIGN KEY (rol) REFERENCES roles(id_rol)
);

CREATE TABLE departamentos (
 id_departamento INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre_departamento VARCHAR(100) NOT NULL,
 descripcion VARCHAR(200)
);

CREATE TABLE puestos (
 id_puesto INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre_puesto VARCHAR(100) NOT NULL,
 descripcion VARCHAR(200),
 salario_base DECIMAL(10,2)
);

CREATE TABLE sedes (
 id_sede INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre_sede VARCHAR(100) NOT NULL,
 pais VARCHAR(100),
 ciudad VARCHAR(100),
 direccion VARCHAR(200),
 telefono VARCHAR(20)
);

CREATE TABLE empleados (
 id_empleado INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100) NOT NULL,
 apellido VARCHAR(100),
 dni VARCHAR(20) UNIQUE,
 email VARCHAR(150),
 telefono VARCHAR(20),
 fecha_nacimiento DATE,
 fecha_contratacion DATE,
 salario DECIMAL(10,2),
 id_departamento INT UNSIGNED,
 id_puesto INT UNSIGNED,
 id_sede INT UNSIGNED,
 activo BOOLEAN DEFAULT TRUE,
 FOREIGN KEY (id_departamento) REFERENCES departamentos(id_departamento),
 FOREIGN KEY (id_puesto) REFERENCES puestos(id_puesto),
 FOREIGN KEY (id_sede) REFERENCES sedes(id_sede)
);

CREATE TABLE proyectos (
 id_proyecto INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 nombre_proyecto VARCHAR(100) NOT NULL,
 descripcion VARCHAR(200),
 fecha_inicio DATE,
 fecha_fin DATE,
 estado VARCHAR(50)
);

CREATE TABLE empleados_proyectos (
 id_empleado INT UNSIGNED,
 id_proyecto INT UNSIGNED,
 rol_proyecto VARCHAR(100),
 horas_asignadas INT,
 PRIMARY KEY (id_empleado, id_proyecto),
 FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
 FOREIGN KEY (id_proyecto) REFERENCES proyectos(id_proyecto)
);

CREATE TABLE historial_laboral (
 id_historial INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 id_empleado INT UNSIGNED,
 puesto_anterior VARCHAR(100),
 puesto_nuevo VARCHAR(100),
 salario_anterior DECIMAL(10,2),
 salario_nuevo DECIMAL(10,2),
 fecha_cambio DATE,
 FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado)
);

CREATE TABLE logs (
 id_log INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 id_usuario INT UNSIGNED,
 accion VARCHAR(50),
 tabla_afectada VARCHAR(50),
 fecha DATETIME,
 FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

-- Índices para búsquedas frecuentes en empleados
CREATE INDEX idx_empleados_departamento ON empleados(id_departamento);
CREATE INDEX idx_empleados_puesto ON empleados(id_puesto);
CREATE INDEX idx_empleados_sede ON empleados(id_sede);
CREATE INDEX idx_empleados_activo ON empleados(activo);

-- Índices para historial laboral
CREATE INDEX idx_historial_empleado ON historial_laboral(id_empleado);
CREATE INDEX idx_historial_fecha ON historial_laboral(fecha_cambio);

-- Índices para proyectos
CREATE INDEX idx_proyectos_estado ON proyectos(estado);
CREATE INDEX idx_proyectos_fechas ON proyectos(fecha_inicio, fecha_fin);

-- Índices para logs
CREATE INDEX idx_logs_usuario ON logs(id_usuario);
CREATE INDEX idx_logs_fecha ON logs(fecha);
CREATE INDEX idx_logs_tabla ON logs(tabla_afectada);

-- Índices para usuarios
CREATE INDEX idx_usuarios_rol ON usuarios(rol);
CREATE INDEX idx_usuarios_activo ON usuarios(activo);

INSERT IGNORE INTO roles (nombre_rol, descripcion) VALUES
('ADMIN','Administrador del sistema'),
('USER','Usuario normal');

INSERT IGNORE INTO departamentos (nombre_departamento) VALUES
('IT'),
('Recursos Humanos'),
('Finanzas'),
('Marketing'),
('Ventas'),
('Logistica'),
('Soporte'),
('Direccion');

INSERT IGNORE INTO puestos (nombre_puesto, salario_base) VALUES
('Programador',30000),
('Analista',35000),
('Gerente',50000),
('Administrador',32000),
('Tecnico',28000),
('Diseñador',30000);

INSERT IGNORE INTO sedes (nombre_sede,pais,ciudad) VALUES
('Madrid','España','Madrid'),
('Barcelona','España','Barcelona'),
('Paris','Francia','Paris'),
('Berlin','Alemania','Berlin'),
('Roma','Italia','Roma');

-- Procedure para generar 50.000 empleados
DELIMITER $$
CREATE PROCEDURE generar_empleados()
BEGIN
 DECLARE i INT DEFAULT 1;
 DECLARE v_nombre VARCHAR(50);
 DECLARE v_apellido VARCHAR(50);
 DECLARE v_numero INT;
 DECLARE v_letra VARCHAR(1);
 WHILE i <= 50000 DO
  SET v_nombre = ELT(FLOOR(1 + RAND()*40), 'Carlos','Laura','Miguel','Ana','Pedro','Sofia','Juan','Maria','Luis','Elena','Pablo','Carmen','Diego','Isabel','Andres','Lucia','Roberto','Marta','Fernando','Patricia','Antonio','Cristina','Manuel','Rosa','Jorge','Teresa','Francisco','Beatriz','Alejandro','Silvia','David','Natalia','Daniel','Raquel','Sergio','Monica','Rafael','Pilar','Alberto','Sandra');
  SET v_apellido = ELT(FLOOR(1 + RAND()*30), 'Garcia','Martinez','Lopez','Sanchez','Gonzalez','Perez','Rodriguez','Fernandez','Moreno','Jimenez','Ruiz','Hernandez','Diaz','Alvarez','Romero','Torres','Navarro','Dominguez','Ramos','Gil','Serrano','Molina','Blanco','Castro','Ortega','Delgado','Marin','Iglesias','Santos','Rubio');
  SET v_numero = 10000000 + i;
  SET v_letra = ELT((v_numero MOD 23) + 1, 'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E');
  INSERT IGNORE INTO empleados(
    nombre,
    apellido,
    dni,
    email,
    telefono,
    fecha_nacimiento,
    fecha_contratacion,
    salario,
    id_departamento,
    id_puesto,
    id_sede
  )
  VALUES(
    v_nombre,
    v_apellido,
	CONCAT(v_numero, v_letra),
    CONCAT(LOWER(v_nombre), '.', LOWER(v_apellido), i, '@empresa.com'),
	CONCAT('6', FLOOR(10000000 + RAND()*89999999)),
    DATE_ADD('1970-01-01', INTERVAL FLOOR(RAND()*10000) DAY),
    DATE_ADD('2015-01-01', INTERVAL FLOOR(RAND()*3000) DAY),
    20000 + FLOOR(RAND()*30000),
    FLOOR(1 + RAND()*8),
    FLOOR(1 + RAND()*6),
    FLOOR(1 + RAND()*5)
  );
  SET i = i + 1;
 END WHILE;
END$$
DELIMITER ;

-- Ejecutarlo
CALL generar_empleados();

-- Procedure para generar 10.000 proyectos
DELIMITER $$
CREATE PROCEDURE generar_proyectos()
BEGIN
 DECLARE i INT DEFAULT 1;
 DECLARE v_fecha_inicio DATE;
 DECLARE v_fecha_fin DATE;
 DECLARE v_prefijo VARCHAR(50);
 DECLARE v_sufijo VARCHAR(50);
 DECLARE v_descripcion VARCHAR(200);
 DECLARE v_estado VARCHAR(20);
 DECLARE v_rand1 INT;
 DECLARE v_rand2 INT;
 DECLARE v_rand3 INT;
 DECLARE v_rand4 INT;
 WHILE i <= 10000 DO
  SET v_rand1 = FLOOR(1 + RAND()*20);
  SET v_rand2 = FLOOR(1 + RAND()*20);
  SET v_rand3 = FLOOR(1 + RAND()*5);
  SET v_rand4 = FLOOR(1 + RAND()*3);
  SET v_fecha_inicio = DATE_ADD('2015-01-01', INTERVAL FLOOR(RAND()*2000) DAY);
  SET v_fecha_fin = DATE_ADD(v_fecha_inicio, INTERVAL FLOOR(90 + RAND()*700) DAY);
  SET v_prefijo = CASE v_rand1
    WHEN 1 THEN 'Desarrollo'
    WHEN 2 THEN 'Implantacion'
    WHEN 3 THEN 'Migracion'
    WHEN 4 THEN 'Optimizacion'
    WHEN 5 THEN 'Diseño'
    WHEN 6 THEN 'Analisis'
    WHEN 7 THEN 'Integracion'
    WHEN 8 THEN 'Modernizacion'
    WHEN 9 THEN 'Automatizacion'
    WHEN 10 THEN 'Gestion'
    WHEN 11 THEN 'Transformacion'
    WHEN 12 THEN 'Actualizacion'
    WHEN 13 THEN 'Implementacion'
    WHEN 14 THEN 'Revision'
    WHEN 15 THEN 'Mejora'
    WHEN 16 THEN 'Creacion'
    WHEN 17 THEN 'Despliegue'
    WHEN 18 THEN 'Mantenimiento'
    WHEN 19 THEN 'Auditoria'
    ELSE 'Formacion'
  END;
  SET v_sufijo = CASE v_rand2
    WHEN 1 THEN 'Web'
    WHEN 2 THEN 'ERP'
    WHEN 3 THEN 'CRM'
    WHEN 4 THEN 'App Movil'
    WHEN 5 THEN 'Base de Datos'
    WHEN 6 THEN 'Infraestructura'
    WHEN 7 THEN 'Red Corporativa'
    WHEN 8 THEN 'Portal Interno'
    WHEN 9 THEN 'Sistema de Pagos'
    WHEN 10 THEN 'E-commerce'
    WHEN 11 THEN 'Plataforma Cloud'
    WHEN 12 THEN 'API REST'
    WHEN 13 THEN 'Panel de Control'
    WHEN 14 THEN 'Seguridad'
    WHEN 15 THEN 'Backup'
    WHEN 16 THEN 'Intranet'
    WHEN 17 THEN 'Facturacion'
    WHEN 18 THEN 'RRHH'
    WHEN 19 THEN 'Logistica'
    ELSE 'Reporting'
  END;
  SET v_descripcion = CASE v_rand3
    WHEN 1 THEN 'Proyecto estrategico para la empresa'
    WHEN 2 THEN 'Iniciativa de mejora continua'
    WHEN 3 THEN 'Proyecto de transformacion digital'
    WHEN 4 THEN 'Optimizacion de procesos internos'
    ELSE 'Proyecto de expansion tecnologica'
  END;
  SET v_estado = CASE v_rand4
    WHEN 1 THEN 'Activo'
    WHEN 2 THEN 'Finalizado'
    ELSE 'Cancelado'
  END;
  INSERT IGNORE INTO proyectos(nombre_proyecto, descripcion, fecha_inicio, fecha_fin, estado)
  VALUES(
    CONCAT(v_prefijo, ' ', v_sufijo, ' ', i),
    v_descripcion,
    v_fecha_inicio,
    v_fecha_fin,
    v_estado
  );
  SET i = i + 1;
 END WHILE;
END$$
DELIMITER ;

-- Ejecutarlo
CALL generar_proyectos();

-- Procedure para generar 200.000 relaciones empleados-proyectos
DELIMITER $$
CREATE PROCEDURE generar_relaciones()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE rol VARCHAR(20);

  WHILE i <= 200000 DO
    SET rol = CASE FLOOR(1 + RAND() * 5)
      WHEN 1 THEN 'Colaborador'
      WHEN 2 THEN 'Lider'
      WHEN 3 THEN 'Analista'
      WHEN 4 THEN 'Desarrollador'
      WHEN 5 THEN 'Tester'
    END;

    INSERT IGNORE INTO empleados_proyectos(
      id_empleado,
      id_proyecto,
      rol_proyecto,
      horas_asignadas
    )
    VALUES(
      FLOOR(1 + RAND() * 50000),
      FLOOR(1 + RAND() * 10000),
      rol,
      FLOOR(10 + RAND() * 200)
    );

    SET i = i + 1;
  END WHILE;
END$$
DELIMITER ;

-- Ejecutarlo
CALL generar_relaciones();