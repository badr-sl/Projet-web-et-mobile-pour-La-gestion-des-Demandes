-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 03 juin 2024 à 01:45
-- Version du serveur : 8.2.0
-- Version de PHP : 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `springpfa`
--

-- --------------------------------------------------------

--
-- Structure de la table `demande`
--

DROP TABLE IF EXISTS `demande`;
CREATE TABLE IF NOT EXISTS `demande` (
  `id_demande` bigint NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `sujet` tinytext,
  `title` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_demande`),
  KEY `FKd18lpvgp0r5chx16yds5643rw` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `demande`
--

INSERT INTO `demande` (`id_demande`, `date`, `etat`, `sujet`, `title`, `user_id`) VALUES
(3, '2024-04-06 16:10:37.717000', 'Solved', 'TISALAT ', 'PROBLEM CONX', 1),
(4, '2024-04-06 16:51:46.505000', 'Solved', 'khaso itsawb', 'problem wifi', 1),
(5, '2024-04-06 17:47:40.087000', 'Rejected', 'test', 'problem test', 7),
(22, '2024-05-16 10:45:15.427000', 'Rejected', 'test', 'test final', 1),
(23, '2024-05-23 09:57:40.247000', 'Solved', 'test', 'test final 5', 1),
(24, '2024-05-23 10:39:52.751000', 'Solved', 'test', 'PFA_lasrt_test', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `user_role_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FKh2wc2dtfdo8maylne7mgubowq` (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `tel`, `username`, `user_role_id`) VALUES
(1, 'a.badido620gmt@gmail.com', '123', '234567890', 'badr', 1),
(2, 'admin@gmail.com', '123', '345674567', 'admin', 2),
(3, 'a.Khalid620gmt@gmail.com', '123', '6546416546519', 'khalid', 1),
(7, 'salah@gmail.com', '123', '061548254', 'salah', 1),
(8, 'saad@gmail.com', '123', '354843548', 'saad', 1),
(9, 'samir@gmail.com', '123', '15485785488', 'samir', 1),
(14, 'kamal@gmail.com', '123', '0654875985', 'kamal', 1),
(15, 'zakaria@gmail.com', '123', '0654851225', 'zakaria', 1),
(16, 'test@gmail.com', '123', '13456789', 'test', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nom_role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user_role`
--

INSERT INTO `user_role` (`id`, `nom_role`) VALUES
(1, 'user'),
(2, 'admin');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `demande`
--
ALTER TABLE `demande`
  ADD CONSTRAINT `FKd18lpvgp0r5chx16yds5643rw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKh2wc2dtfdo8maylne7mgubowq` FOREIGN KEY (`user_role_id`) REFERENCES `user_role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
