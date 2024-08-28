<?php
session_start();
$conn = new mysqli('localhost', 'root', '', 'gestionale_swe');
if ($conn->connect_error) {
    die('Errore di connessione (' . $conn->connect_errno . ') ' . $conn->connect_error);
}
$conn->set_charset('utf8');
?>