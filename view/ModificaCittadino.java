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
import model.Licenza;
import model.Utente;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ModificaCittadino extends ViewInterface{
    @FXML
    private MenuItem menuHome;

    @FXML
    private TextField idCittadino;
    @FXML
    private TextField nomeCittadino;
    @FXML
    private TextField cognomeCittadino;
    @FXML
    private TextField emailCittadino;
    @FXML
    private TextField pswCittadino;

    @FXML
    private Label codiceLicenza;
    @FXML
    private Label scadenzaLicenza;
    @FXML
    private Button btnLicenza;
    @FXML
    private Button btnDeleteLicenza;
    @FXML
    private CheckBox confermaEliminazione;
    @FXML
    private Button btnDeleteCittadino;

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

        //Carico i dati del cittadino selezionato nei campi di testo
        Utente cittadinoSelezionato = controller.getUtenteSelezionato();
        idCittadino.setText(String.valueOf(cittadinoSelezionato.getId()));
        nomeCittadino.setText(cittadinoSelezionato.getNome());
        cognomeCittadino.setText(cittadinoSelezionato.getCognome());
        emailCittadino.setText(cittadinoSelezionato.getEmail());

        Licenza licenza = controller.getLicenzaCittadino(cittadinoSelezionato.getId());
        String data_scadenza = LocalDate.now().plusYears(1).toString();
        if (licenza != null) {
            //Recupera la data di scadenza e la formatta
            String dataScadenza = licenza.getScadenza();
            codiceLicenza.setText(licenza.getCodice());
            String[] data = dataScadenza.split("-");
            scadenzaLicenza.setText(data[2] + "/" + data[1] + "/" + data[0]);
            btnLicenza.setText("Rinnova Licenza");
            if(LocalDate.now().isBefore(LocalDate.parse(dataScadenza))){
                btnLicenza.setDisable(true);
            }else{
                btnLicenza.setOnAction(e -> {
                    try {
                        controller.onUpdateLicenza(licenza.getId(), cittadinoSelezionato.getId(), licenza.getCodice(), data_scadenza);
                        controller.setViewAttuale(new ModificaCittadino(controller, stage));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        } else {
            btnLicenza.setText("Genera Licenza");
            btnLicenza.setOnAction(e -> {
                try {
                    StringBuilder codice_generato;
                    do {
                        codice_generato = new StringBuilder();
                        for (int i = 0; i < 6; i++) {
                            codice_generato.append((int) (Math.random() * 10));
                        }
                    } while (!controller.onAddLicenza(cittadinoSelezionato.getId(), codice_generato.toString(), data_scadenza));
                    controller.setViewAttuale(new ModificaCittadino(controller, stage));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            btnDeleteLicenza.setDisable(true);
        }

    }

    public ModificaCittadino(Controller c, Stage stage) {
        super(c, "Modifica Cittadino", "fxml/ModificaCittadino.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Modifica cittadino");
        }
    }
}
