<!DOCTYPE html>
<html lang="en">

<head>
    <title></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://cdn.rawgit.com/serratus/quaggaJS/0420d5e0/dist/quagga.min.js"></script>
    <script src="js/jquery.js"></script>
    <script src="js/index.js"></script>
    <style>
        /* In order to place the tracking correctly */
        canvas.drawing, canvas.drawingBuffer {
            position: absolute;
            top: 0;
        }
    </style>
    <link href="css/flexbox.css" rel="stylesheet">
    <link href="css/index.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>

<body>
    <div class="container">
        <div class="row">
            <div class="col-md-12 center_column">
                <br>
                <h1>Applicativo SWE</h1>
            </div>
            <div class="col-md-12 center_column">
                <br><br>
                <h4><i>Con questo strumento potrai verificare se un biglietto &egrave; valido o meno</i></h4>
                <br><br>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 center_column" style="position: relative;">
                <div id="scanner-container" class="center_column"></div>
                <input type="button" id="btn" value="On/Off Scansione" onclick="init()"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 center_column">
                <br><br>
                <h5><i>oppure</i></h5>
            </div>
            <div class="col-md-12 center_column">
                <br><br>
                <input type="file" id="fileInput" accept="image/*">
                <div id="imageContainer"></div>
            </div>
        </div>
    </div>
</body>

</html>