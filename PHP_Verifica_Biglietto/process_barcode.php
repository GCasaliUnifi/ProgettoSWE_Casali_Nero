<?php
require_once "config.php";

if (isset($conn)&& isset($_POST["barcode"])) {
    $barcode = strval($_POST["barcode"]);
    $codici = explode("-", $barcode);
    if(count($codici) == 2){
        $id_biglietto = intval($codici[0]);
        $codf = strtoupper($codici[1]);
        $sql = "SELECT id FROM biglietto WHERE id = $id_biglietto AND codf = '$codf'";
        $result = $conn->query($sql);
        if ($result->num_rows == 1) {
            echo "1";
        } else {
            echo "0";
        }
    }else{
        echo "0";
    }
} else {
    echo "0";
}
?>