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

public class AggiungiPadiglione extends ViewInterface{
    @FXML
    private TextField codePadiglione;
    @FXML
    private TextField dimPadiglione;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;
    @FXML
    private MenuItem menuHome;

    @FXML
    public void initialize() {
        menuHome.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnConfirm.setOnAction(event -> {
            if(codePadiglione.getText().isEmpty() || dimPadiglione.getText().isEmpty()) {
                System.out.println("Errore: uno o più campi sono vuoti!");
            } else {
                try {
                    controller.onAddPadiglione(codePadiglione.getText(), Float.parseFloat(dimPadiglione.getText()));
                } catch (NumberFormatException e) {
                    controller.alert(Alert.AlertType.ERROR, "Inserire un numero valido!");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCancel.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(this.controller, stage));
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }


    public AggiungiPadiglione(Controller c, Stage stage) {
        super(c, "Dashboard", "fxml/AggiungiPadiglione.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di aggiunta padiglione");
        }
    }
}
