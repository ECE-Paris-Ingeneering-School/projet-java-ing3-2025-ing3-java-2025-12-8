-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 19, 2025 at 10:30 AM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopping`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrateur`
--

CREATE TABLE `administrateur` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `article`
--

CREATE TABLE `article` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `marque` varchar(100) NOT NULL,
  `prix_unitaire` decimal(10,2) NOT NULL,
  `prix_gros` decimal(10,2) DEFAULT NULL,
  `quantite_gros` int(11) DEFAULT NULL,
  `id_marque` int(11) NOT NULL,
  `quantite_stock` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `article`
--

INSERT INTO `article` (`id`, `nom`, `marque`, `prix_unitaire`, `prix_gros`, `quantite_gros`, `id_marque`, `quantite_stock`) VALUES
(1, 'T-shirt Running', 'Nike', '29.99', '100.00', 5, 1, 8),
(2, 'Chaussures Sport', 'Adidas', '89.90', NULL, NULL, 2, 2),
(3, 'iPhone 14', 'Apple', '999.00', NULL, NULL, 3, 14),
(4, 'AirPods Pro', 'Apple', '279.00', '250.00', 3, 3, 4),
(5, 'Galaxy S23', 'Samsung', '899.00', NULL, NULL, 4, 0),
(6, 'Pantalon de sport', 'Nike', '59.90', '200.00', 4, 1, 0),
(7, 'Sweat Capuche', 'Adidas', '69.90', '250.00', 5, 2, 0),
(10, 'ok', 'okito', '12.00', '12.00', NULL, 6, 10);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande` (
  `id` int(11) NOT NULL,
  `date_commande` datetime DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(10,2) DEFAULT NULL,
  `id_utilisateur` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`id`, `date_commande`, `total`, `id_utilisateur`) VALUES
(14, '2025-04-18 20:35:36', '29.99', 1),
(15, '2025-04-18 22:41:31', '36.00', 1),
(16, '2025-04-18 23:05:35', '5994.00', 3),
(17, '2025-04-19 11:45:19', '29.99', 1),
(18, '2025-04-19 11:46:08', '89.90', 1),
(19, '2025-04-19 11:50:05', '89.90', 1),
(20, '2025-04-19 11:53:30', '89.90', 1);

-- --------------------------------------------------------

--
-- Table structure for table `ligne_commande`
--

CREATE TABLE `ligne_commande` (
  `id_commande` int(11) NOT NULL,
  `id_article` int(11) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prix` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ligne_commande`
--

INSERT INTO `ligne_commande` (`id_commande`, `id_article`, `quantite`, `prix`) VALUES
(14, 1, 1, 29.99),
(16, 3, 6, 999),
(17, 1, 1, 29.99),
(18, 2, 1, 89.9),
(19, 2, 1, 89.9),
(20, 2, 1, 89.9);

-- --------------------------------------------------------

--
-- Table structure for table `marque`
--

CREATE TABLE `marque` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `marque`
--

INSERT INTO `marque` (`id`, `nom`) VALUES
(2, 'Adidas'),
(3, 'Apple'),
(1, 'Nike'),
(6, 'okito'),
(4, 'Samsung');

-- --------------------------------------------------------

--
-- Table structure for table `reduction`
--

CREATE TABLE `reduction` (
  `id` int(11) NOT NULL,
  `id_article` int(11) NOT NULL,
  `seuil_quantite` int(11) DEFAULT NULL,
  `prix_remise` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mot_de_passe` varchar(255) NOT NULL,
  `type` enum('client','admin') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `nom`, `email`, `mot_de_passe`, `type`) VALUES
(1, 'Douady', 'Elias', 'nanterre', 'client'),
(2, 'Admin', 'admin@admin.com', 'admin123', 'admin'),
(3, 'Samil', 'samil@gmail.com', 'samil', 'client'),
(4, 'mouss', 'mouss@gmail.com', 'mouss', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrateur`
--
ALTER TABLE `administrateur`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_marque` (`id_marque`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_commande_utilisateur` (`id_utilisateur`);

--
-- Indexes for table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD PRIMARY KEY (`id_commande`,`id_article`),
  ADD KEY `id_article` (`id_article`);

--
-- Indexes for table `marque`
--
ALTER TABLE `marque`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nom` (`nom`);

--
-- Indexes for table `reduction`
--
ALTER TABLE `reduction`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_article` (`id_article`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `article`
--
ALTER TABLE `article`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `marque`
--
ALTER TABLE `marque`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `reduction`
--
ALTER TABLE `reduction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `administrateur`
--
ALTER TABLE `administrateur`
  ADD CONSTRAINT `administrateur_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `fk_marque` FOREIGN KEY (`id_marque`) REFERENCES `marque` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilisateur` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_commande_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`);

--
-- Constraints for table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD CONSTRAINT `ligne_commande_ibfk_1` FOREIGN KEY (`id_commande`) REFERENCES `commande` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ligne_commande_ibfk_2` FOREIGN KEY (`id_article`) REFERENCES `article` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `reduction`
--
ALTER TABLE `reduction`
  ADD CONSTRAINT `reduction_ibfk_1` FOREIGN KEY (`id_article`) REFERENCES `article` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
