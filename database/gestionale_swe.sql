/*!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.8-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: gestionale_swe
-- ------------------------------------------------------
-- Server version	10.11.8-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `biglietto`
--

DROP TABLE IF EXISTS `biglietto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `biglietto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_evento` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nome` text NOT NULL,
  `cognome` text NOT NULL,
  `codf` text NOT NULL,
  `data_prenotazione` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biglietto`
--

LOCK TABLES `biglietto` WRITE;
/*!40000 ALTER TABLE `biglietto` DISABLE KEYS */;
INSERT INTO `biglietto` VALUES
(1,2,3,'Gianfederico','Milani','RLNRLN97A01A007D','2024-08-19'),
(2,2,3,'Claudio','Palmieri','LMBRDO97A01A007D','2024-08-19'),
(3,4,3,'Giancarlo','D\'Angelo','MRTMRT97A01A007D','2024-08-19'),
(4,4,3,'Luca','Pellegrini','LNGGNT97A01A007D','2024-08-20');
/*!40000 ALTER TABLE `biglietto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codice` text NOT NULL,
  `nome` text NOT NULL,
  `data` date NOT NULL,
  `descrizione` text NOT NULL,
  `posti` int(11) NOT NULL DEFAULT 100,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES
(2,'E_01','Fiera dei giochi','2024-08-10','Questa è la descrizione per la fiera dei giochi!\nIngresso Gratuito per i luogotenenti.',98),
(4,'E_03','Sagra del tartufo','2024-08-31','Questa sagra sarà piena di funghi.\nIngresso gratuito per tutti.',98);
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `licenza`
--

DROP TABLE IF EXISTS `licenza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `licenza` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `codice` text NOT NULL,
  `scadenza` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `codice` (`codice`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `licenza`
--

LOCK TABLES `licenza` WRITE;
/*!40000 ALTER TABLE `licenza` DISABLE KEYS */;
INSERT INTO `licenza` VALUES
(7,3,'619953','2025-08-20');
/*!40000 ALTER TABLE `licenza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifica`
--

DROP TABLE IF EXISTS `notifica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifica` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `data` datetime NOT NULL DEFAULT current_timestamp(),
  `messaggio` text DEFAULT NULL,
  `stato` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifica`
--

LOCK TABLES `notifica` WRITE;
/*!40000 ALTER TABLE `notifica` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `padiglione`
--

DROP TABLE IF EXISTS `padiglione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `padiglione` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codice` text NOT NULL,
  `dimensione` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `padiglione`
--

LOCK TABLES `padiglione` WRITE;
/*!40000 ALTER TABLE `padiglione` DISABLE KEYS */;
INSERT INTO `padiglione` VALUES
(1,'PD_01','60.25'),
(2,'PD_02','85.23');
/*!40000 ALTER TABLE `padiglione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `padiglione_evento`
--

DROP TABLE IF EXISTS `padiglione_evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `padiglione_evento` (
  `id_evento` int(11) NOT NULL,
  `id_padiglione` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  KEY `id_evento` (`id_evento`),
  KEY `id_padiglione` (`id_padiglione`),
  KEY `id_utente` (`id_utente`),
  CONSTRAINT `padiglione_evento_ibfk_1` FOREIGN KEY (`id_evento`) REFERENCES `evento` (`id`),
  CONSTRAINT `padiglione_evento_ibfk_2` FOREIGN KEY (`id_padiglione`) REFERENCES `padiglione` (`id`),
  CONSTRAINT `padiglione_evento_ibfk_3` FOREIGN KEY (`id_utente`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `padiglione_evento`
--

LOCK TABLES `padiglione_evento` WRITE;
/*!40000 ALTER TABLE `padiglione_evento` DISABLE KEYS */;
/*!40000 ALTER TABLE `padiglione_evento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` text NOT NULL,
  `lastName` text NOT NULL,
  `email` text NOT NULL,
  `cellphone` text NOT NULL,
  `psw` text NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(2,'Edoardo','Nero','edoardo@gmail.com','3669719064','d94b80a177cb51935145fed566d48d5bc9fd982b24d271c86b3259a95e786bc34174905efb97d0f16130da807813e20ad52200ddc6969ffd4788fb8f6fb1f557',1),
(3,'Giacomone','Casalone','giacomo@gmail.com','3334455678','d94b80a177cb51935145fed566d48d5bc9fd982b24d271c86b3259a95e786bc34174905efb97d0f16130da807813e20ad52200ddc6969ffd4788fb8f6fb1f557',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-20 17:47:27
