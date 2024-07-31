package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModificaPadiglione extends ViewInterface {
    @FXML
    private MenuItem menuHome;

    @FXML
    private TextField idModifica;
    @FXML
    private TextField codiceModifica;
    @FXML
    private TextField dimensioneModifica;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    public void initialize() throws SQLException {
        menuHome.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //Carico i dati del padiglione selezionato nei campi di testo
        idModifica.setText(String.valueOf(controller.getPadiglioneSelezionato().getId()));
        codiceModifica.setText(controller.getPadiglioneSelezionato().getCodice());
        dimensioneModifica.setText(String.valueOf(controller.getPadiglioneSelezionato().getDimensione()));

        //Se clicco sul bottone salva, aggiorno i dati del padiglione selezionato
        btnSave.setOnAction(event -> {
            if(idModifica.getText().isEmpty() || codiceModifica.getText().isEmpty() || dimensioneModifica.getText().isEmpty()) {
                controller.alert(Alert.AlertType.WARNING, "Uno o più campi sono vuoti!");
            } else {
                try {
                    if(controller.onUpdatePadiglione(Integer.parseInt(idModifica.getText()), codiceModifica.getText(), Float.parseFloat(dimensioneModifica.getText()))){
                        controller.alert(Alert.AlertType.INFORMATION, "Padiglione aggiornato con successo!");
                        controller.setViewAttuale(new ListaPadiglioni(this.controller, stage));
                    }
                } catch (NumberFormatException e) {
                    controller.alert(Alert.AlertType.ERROR, "Inserire una dimensione valida!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Se clicco sul bottone annulla, torno alla lista dei padiglioni
        btnCancel.setOnAction(event -> {
            try {
                controller.setViewAttuale(new ListaPadiglioni(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ModificaPadiglione(Controller c, Stage stage) {
        super(c, "Modifica Padiglione", "fxml/ModificaPadiglione.fxml");
        ViewInterface.stage = stage;
    }

    @Override
    public void display() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(this);
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            throw new IOException("Errore nel caricamento della finestra di Modifica padiglione");
        }
    }
}
