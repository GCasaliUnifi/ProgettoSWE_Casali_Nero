package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends ViewInterface {
    @FXML
    private Button btnAggiungiEvento;
    @FXML
    private Button btnAggiungiPadiglione;
    @FXML
    private MenuItem menuPadiglioni;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private Button btnLogOut;

    @FXML
    public void initialize() {
        menuPadiglioni.setOnAction(event -> {
            try{
                controller.setViewAttuale(new ListaPadiglioni(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        menuEventi.setOnAction(event -> {
            try {
                controller.setViewAttuale(new ListaEventi(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnLogOut.setOnAction(event -> {
            try {
                controller.setViewAttuale(new LogIn(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        if(this.controller.isAmministratore()) {
            btnAggiungiPadiglione.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new AggiungiPadiglione(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            btnAggiungiEvento.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new AggiungiEvento(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            //TODO: Implementare la funzionalità per il cittadino
        }

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
