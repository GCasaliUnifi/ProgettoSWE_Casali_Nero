-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Creato il: Ago 11, 2024 alle 00:04
-- Versione del server: 10.4.28-MariaDB
-- Versione PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionale_swe`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `evento`
--

CREATE TABLE `evento` (
  `id` int(11) NOT NULL,
  `codice` text NOT NULL,
  `nome` text NOT NULL,
  `data` date NOT NULL,
  `descrizione` text NOT NULL,
  `posti` int(11) NOT NULL DEFAULT 100
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `evento`
--

INSERT INTO `evento` (`id`, `codice`, `nome`, `data`, `descrizione`, `posti`) VALUES
(2, 'E_01', 'Fiera dei giochi', '2024-08-10', 'Questa è la descrizione per la fiera dei giochi!\nIngresso Gratuito per i luogotenenti.', 100),
(4, 'E_03', 'Sagra del tartufo', '2024-08-31', 'Questa sagra sarà piena di funghi.\nIngresso gratuito per tutti.', 100);

-- --------------------------------------------------------

--
-- Struttura della tabella `licenza`
--

CREATE TABLE `licenza` (
  `id` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `codice` text NOT NULL,
  `scadenza` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `licenza`
--

INSERT INTO `licenza` (`id`, `id_user`, `codice`, `scadenza`) VALUES
(6, 3, '623910', '2025-08-08');

-- --------------------------------------------------------

--
-- Struttura della tabella `notifica`
--

CREATE TABLE `notifica` (
  `id` int(11) NOT NULL,
  `tipo` int(11) NOT NULL,
  `id_utente` int(11) NOT NULL,
  `data` datetime NOT NULL DEFAULT current_timestamp(),
  `messaggio` text DEFAULT NULL,
  `stato` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `padiglione`
--

CREATE TABLE `padiglione` (
  `id` int(11) NOT NULL,
  `codice` text NOT NULL,
  `dimensione` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `padiglione`
--

INSERT INTO `padiglione` (`id`, `codice`, `dimensione`) VALUES
(1, 'PD_01', '60.25'),
(2, 'PD_02', '85.23');

-- --------------------------------------------------------

--
-- Struttura della tabella `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `firstName` text NOT NULL,
  `lastName` text NOT NULL,
  `email` text NOT NULL,
  `cellphone` text NOT NULL,
  `psw` text NOT NULL,
  `type` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `user`
--

INSERT INTO `user` (`id`, `firstName`, `lastName`, `email`, `cellphone`, `psw`, `type`) VALUES
(2, 'Edoardo', 'Nero', 'edoardo@gmail.com', '3669719064', 'd94b80a177cb51935145fed566d48d5bc9fd982b24d271c86b3259a95e786bc34174905efb97d0f16130da807813e20ad52200ddc6969ffd4788fb8f6fb1f557', 1),
(3, 'Giacomone', 'Casalone', 'giacomo@gmail.com', '3334455678', 'd94b80a177cb51935145fed566d48d5bc9fd982b24d271c86b3259a95e786bc34174905efb97d0f16130da807813e20ad52200ddc6969ffd4788fb8f6fb1f557', 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `evento`
--
ALTER TABLE `evento`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `licenza`
--
ALTER TABLE `licenza`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `codice` (`codice`) USING HASH;

--
-- Indici per le tabelle `notifica`
--
ALTER TABLE `notifica`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `padiglione`
--
ALTER TABLE `padiglione`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `evento`
--
ALTER TABLE `evento`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT per la tabella `licenza`
--
ALTER TABLE `licenza`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT per la tabella `notifica`
--
ALTER TABLE `notifica`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `padiglione`
--
ALTER TABLE `padiglione`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
