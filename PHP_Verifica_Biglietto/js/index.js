$(document).ready(function(){
    document.getElementById('fileInput').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.style.maxWidth = '100%';
                document.getElementById('imageContainer').innerHTML = '';
                document.getElementById('imageContainer').appendChild(img);

                Quagga.decodeSingle({
                    src: e.target.result,
                    numOfWorkers: 0,  // Needs to be 0 when used within a browser
                    inputStream: {
                        size: 800  // restrict input-size to be 800px in width (long-side)
                    },
                    decoder: {
                        readers: ["code_128_reader"] // List of active readers
                    },
                }, function(result) {
                    if(result && result.codeResult) {
                        $.post("process_barcode.php", { barcode: result.codeResult.code }, function(data){
                            if(data === "1"){
                                alert("Biglietto Valido!");
                                location.reload();
                            }else{
                                alert("Biglietto NON Valido! : "+data);
                                location.reload();
                            }
                        });
                    } else {
                        alert("Codice non riconosciuto!");
                        location.reload();
                    }
                });
            };
            reader.readAsDataURL(file);
        }
    });
});

var _scannerIsRunning = false;

function init(){
    if (_scannerIsRunning) {
        Quagga.stop();
        location.reload();
    } else {
        startScanner();
    }
}

function startScanner() {
    Quagga.init({
        inputStream: {
            name: "Live",
            type: "LiveStream",
            target: document.querySelector('#scanner-container'),
            constraints: {
                width: 480,
                height: 320,
                facingMode: "environment"
            },
        },
        decoder: {
            readers: [
                "code_128_reader"
            ],
            debug: {
                showCanvas: true,
                showPatches: true,
                showFoundPatches: true,
                showSkeleton: true,
                showLabels: true,
                showPatchLabels: true,
                showRemainingPatchLabels: true,
                boxFromPatches: {
                    showTransformed: true,
                    showTransformedBox: true,
                    showBB: true
                }
            }
        },

    }, function (err) {
        if (err) {
            console.log(err);
            return
        }

        console.log("Initialization finished. Ready to start");
        Quagga.start();

        // Set flag to is running
        _scannerIsRunning = true;
    });

    Quagga.onProcessed(function (result) {
        var drawingCtx = Quagga.canvas.ctx.overlay,
            drawingCanvas = Quagga.canvas.dom.overlay;

        if (result) {
            if (result.boxes) {
                drawingCtx.clearRect(0, 0, parseInt(drawingCanvas.getAttribute("width")), parseInt(drawingCanvas.getAttribute("height")));
                result.boxes.filter(function (box) {
                    return box !== result.box;
                }).forEach(function (box) {
                    Quagga.ImageDebug.drawPath(box, { x: 0, y: 1 }, drawingCtx, { color: "green", lineWidth: 2 });
                });
            }

            if (result.box) {
                Quagga.ImageDebug.drawPath(result.box, { x: 0, y: 1 }, drawingCtx, { color: "#00F", lineWidth: 2 });
            }

            if (result.codeResult && result.codeResult.code) {
                Quagga.ImageDebug.drawPath(result.line, { x: 'x', y: 'y' }, drawingCtx, { color: 'red', lineWidth: 3 });
            }
        }
    });


    Quagga.onDetected(function (result) {
        $.post("process_barcode.php", { barcode: result.codeResult.code }, function(data){
            if(data === "1"){
                alert("Biglietto Valido!");
                location.reload();
            }else{
                alert("Biglietto NON Valido! : "+data);
                location.reload();
            }
        });
    });
}