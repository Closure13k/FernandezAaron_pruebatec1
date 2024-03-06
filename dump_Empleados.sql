-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 06-03-2024 a las 12:39:24
-- Versión del servidor: 8.2.0
-- Versión de PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `empleados`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

DROP TABLE IF EXISTS `empleados`;
CREATE TABLE IF NOT EXISTS `empleados` (
  `id_empleado` int NOT NULL AUTO_INCREMENT,
  `cuenta_activa` tinyint(1) NOT NULL DEFAULT '0',
  `fecha_contratacion` date NOT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `dni_nif` char(9) COLLATE utf8mb4_spanish_ci NOT NULL,
  `cargo` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `salario` double NOT NULL,
  `apellido` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`id_empleado`),
  UNIQUE KEY `UQ_Empleados_DniNif` (`dni_nif`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`id_empleado`, `cuenta_activa`, `fecha_contratacion`, `nombre`, `dni_nif`, `cargo`, `salario`, `apellido`) VALUES
(1, 1, '2012-12-12', 'Aaron', '12345678A', 'Gerente', 1200, 'Pruebas'),
(2, 0, '2012-12-12', 'María', '98765432B', 'Analista', 3900, 'García'),
(3, 1, '2024-02-10', 'Pedro', '56789012C', 'Desarrollador', 4000, 'Martínez'),
(4, 0, '2023-11-05', 'Ana', '34567890D', 'Diseñador', 3800, 'López'),
(5, 0, '2022-08-30', 'Luis', '90123456E', 'Contador', 4500, 'Rodríguez');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
