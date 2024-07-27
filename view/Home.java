package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends ViewInterface {
    @FXML
    private Button btnAggiungiPadiglione;

    @FXML
    public void initialize() {
        btnAggiungiPadiglione.setOnAction(event -> {
            try {
                controller.setViewAttuale(new AggiungiPadiglione(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Home(Controller c, Stage stage) {
        super(c, "Dashboard", c.isAmministratore() ? "fxml/DashboardImpiegatoC.fxml" : "fxml/DashboardCittadino.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di dashboard");
        }
    }
}
