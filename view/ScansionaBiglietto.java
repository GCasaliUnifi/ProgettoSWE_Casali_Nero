package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Evento;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScansionaBiglietto extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuPadiglioni;
    @FXML
    private TextField verificaBiglietto;
    @FXML
    private Button btnVerifica;

    @FXML
    public void initialize() throws SQLException {
        menuHome.setOnAction(e -> {
            try {
                controller.setViewAttuale(new Home(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        menuPadiglioni.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaPadiglioni(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnVerifica.setOnAction(e -> {
            try {
                if(!verificaBiglietto.getText().isEmpty()){
                    controller.onVerifyBiglietto(verificaBiglietto.getText());
                }else{
                    controller.alert(Alert.AlertType.WARNING, "Inserire un codice biglietto");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

    }

    public ScansionaBiglietto(Controller c, Stage stage) {
        super(c, "Verifica Biglietto", "fxml/ScansionaBiglietto.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Scansiona Biglietto");
        }
    }
}
