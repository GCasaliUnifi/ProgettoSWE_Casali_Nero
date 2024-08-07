package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Licenza;

import java.io.IOException;
import java.sql.SQLException;

public class Home extends ViewInterface {
    @FXML
    private MenuItem menuPadiglioni;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private Button btnLogOut;

    //IC
    @FXML
    private Button btnAggiungiEvento;
    @FXML
    private Button btnAggiungiPadiglione;
    @FXML
    private Button btnListaCittadini;

    //Cittadino
    @FXML
    private Label codiceLicenza;
    @FXML
    private Label scadenzaLicenza;
    @FXML
    private Button btnRichiediLicenza;

    @FXML
    public void initialize() throws SQLException {
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

            btnListaCittadini.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new ListaCittadini(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            //TODO: Implementare la funzionalità per il cittadino
            Licenza licenza = controller.getLicenzaCittadino();
            if(licenza == null) {
                codiceLicenza.setText("Nessuna licenza");
                scadenzaLicenza.setText("Nessuna licenza");
            }else{
                codiceLicenza.setText(licenza.getCodice());
                String data_ita = licenza.getScadenza().substring(8, 10) + "/" + licenza.getScadenza().substring(5, 7) + "/" + licenza.getScadenza().substring(0, 4);
                scadenzaLicenza.setText(data_ita);
                btnRichiediLicenza.setDisable(true);
            }

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
