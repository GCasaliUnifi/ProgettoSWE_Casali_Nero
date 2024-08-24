package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Evento;
import model.Licenza;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrenotaPadiglione extends ViewInterface {
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private TextField codePadiglone;
    @FXML
    private TextField dimPadiglione;
    @FXML
    private ChoiceBox<Evento> eventSelector;
    @FXML
    private ChoiceBox<String> typeSelector;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;


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

        menuEventi.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaEventi(controller, stage));
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

        btnConfirm.setOnAction(e -> {
            if((eventSelector.getValue() != null) && (typeSelector.getValue() != null)) {
                try {
                    Licenza licenza = controller.getLicenzaCittadino();
                    if(licenza != null) {
                        if(!controller.isLicenzaScaduta(licenza)){
                            if(controller.onPrenotaPadiglione(eventSelector.getValue().getId(), typeSelector.getItems().indexOf(typeSelector.getValue()))) {
                                controller.alert(Alert.AlertType.INFORMATION, "Padiglione prenotato con successo");
                                controller.setViewAttuale(new ListaPadiglioni(controller, stage));
                            }else{
                                controller.alert(Alert.AlertType.ERROR, "Errore nella prenotazione del padiglione");
                            }
                        } else {
                            System.out.println("Licenza scaduta!");
                        }
                    } else {
                        System.out.println("Utente non ha licenza!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.println("Selezionare qualcosa!");
            }
        });

        btnCancel.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaPadiglioni(this.controller, stage));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        codePadiglone.setText(controller.getPadiglioneSelezionato().getCodice());
        dimPadiglione.setText(String.valueOf(controller.getPadiglioneSelezionato().getDimensione()));
        ArrayList<Evento> listaEventi = controller.getListaEventiDisponibili(controller.getPadiglioneSelezionato().getId());

        if(listaEventi != null) {
            eventSelector.getItems().addAll(listaEventi);
        }

        System.out.println(controller.getListaEventiDisponibili(controller.getPadiglioneSelezionato().getId()));
        typeSelector.getItems().addAll("Ristoro", "Commerciale", "Intrattenimento", "Altro...");
    }

    public PrenotaPadiglione(Controller c, Stage stage) {
        super(c, "Prenota Padiglione", "fxml/PrenotaPadiglione.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Prenotazione Padiglione: "+e.getMessage());
        }
    }
}
