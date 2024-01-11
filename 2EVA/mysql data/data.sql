

DROP DATABASE IF exists ACCESODATA;

CREATE DATABASE ACCESODATA;

USE ACCESODATA;


DROP TABLE IF exists DEPARTAMENTOS;

CREATE TABLE DEPARTAMENTOS(

dept_no tinyint(2) not null,
dnombre varchar(15) not null,
loc varchar(15) not null,

 CONSTRAINT pk_dept PRIMARY KEY (dept_no)
);

DROP TABLE IF exists EMPLEADOS;

CREATE TABLE EMPLEADOS(

emp_no smallint(4) primary key not null,
apellido varchar(10)not null,
oficio varchar(10)not null,
dir smallint(4) not null,
fecha_alt DATE NOT NULL DEFAULT(current_date()),
salario float(6,2) not null,
comision float(6,2) default 0 not null,
dept_no tinyint(2) not null,
FOREIGN KEY (dir) REFERENCES EMPLEADOS(emp_no),
FOREIGN KEY (dept_no) REFERENCES DEPARTAMENTOS(dept_no)

);



-- Insertar 10 registros en la tabla DEPARTAMENTOS
INSERT INTO DEPARTAMENTOS (dept_no, dnombre, loc) VALUES
(1, 'Ventas', 'Madrid'),
(2, 'Desarrollo', 'Barcelona'),
(3, 'Soporte', 'Valencia'),
(4, 'Marketing', 'Sevilla'),
(5, 'Recursos', 'Bilbao'),
(6, 'Finanzas', 'Zaragoza'),
(7, 'Logística', 'Alicante'),
(8, 'Investigación', 'Málaga'),
(9, 'Calidad', 'Murcia'),
(10, 'Legal', 'Granada');

-- Insertar 30 registros en la tabla EMPLEADOS
INSERT INTO EMPLEADOS (emp_no, apellido, oficio, dir, fecha_alt, salario, comision, dept_no) VALUES
(0, 'Roberto', 'Dev', 0, '2022-01-01', 9000.00, 9000.05, 2),
(1, 'González', 'Dev', 1, '2022-01-01', 5000.00, 0.05, 2),
(2, 'Martínez', 'Analista', 1, '2022-02-01', 6000.00, 0.02, 1),
(3, 'López', 'Diseñador', 1, '2022-03-01', 5500.00, 0.03, 3),
(4, 'Sánchez', 'Dev', 2, '2022-04-01', 5200.00, 0.02, 2),
(5, 'Rodrígo', 'Gerente', 0, '2022-05-01', 7000.00, 0.1, 1),
(6, 'Fernández', 'Analista', 1, '2022-06-01', 5800.00, 0.03, 2),
(7, 'Pérez', 'Diseñador', 3, '2022-07-01', 5600.00, 0.02, 3),
(8, 'Hernández', 'Dev', 2, '2022-08-01', 5300.00, 0.02, 2),
(9, 'Díaz', 'Analista', 1, '2022-09-01', 5900.00, 0.03, 1),
(10, 'Gómez', 'Gerente', 0, '2022-10-01', 7200.00, 0.1, 3),
(11, 'Molina', 'Analista', 1, '2022-11-01', 5800.00, 0.03, 2),
(12, 'Suárez', 'Dev', 2, '2022-12-01', 5400.00, 0.02, 3),
(13, 'Cabrera', 'Analista', 1, '2023-01-01', 6000.00, 0.03, 1),
(14, 'Gutiérrez', 'Diseñador', 3, '2023-02-01', 5600.00, 0.02, 2),
(15, 'Romero', 'Dev', 2, '2023-03-01', 5200.00, 0.02, 3),
(16, 'Ortega', 'Analista', 1, '2023-04-01', 5900.00, 0.03, 1),
(17, 'Rivas', 'Gerente', 0, '2023-05-01', 7100.00, 0.1, 2),
(18, 'Cortés', 'Diseñador', 3, '2023-06-01', 5500.00, 0.02, 3),
(19, 'Mendez', 'Analista', 1, '2023-07-01', 6000.00, 0.03, 2),
(20, 'Guerra', 'Dev', 2, '2023-08-01', 5300.00, 0.02, 1),
(21, 'Salazar', 'Analista', 1, '2023-09-01', 5900.00, 0.03, 3),
(22, 'Jiménez', 'Gerente', 0, '2023-10-01', 7000.00, 0.1, 2),
(23, 'Cruz', 'Dev', 2, '2023-11-01', 5400.00, 0.02, 1),
(24, 'Vargas', 'Diseñador', 3, '2023-12-01', 5600.00, 0.02, 3),
(25, 'Olivares', 'Analista', 1, '2024-01-01', 6000.00, 0.03, 2),
(26, 'Soto', 'Dev', 2, '2024-02-01', 5300.00, 0.02, 1),
(27, 'Núñez', 'Gerente', 27, '2024-03-01', 7200.00, 0.1, 3),
(28, 'Vega', 'Analista', 1, '2024-04-01', 5900.00, 0.03, 1),
(29, 'Lara', 'Diseñador', 3, '2024-05-01', 5500.00, 0.02, 2),
(30, 'Montes', 'Dev', 2, '2024-06-01', 5200.00, 0.02, 3);

