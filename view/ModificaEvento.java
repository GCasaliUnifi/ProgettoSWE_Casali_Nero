package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Licenza;
import model.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModificaEvento extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private Label codiceEvento;
    @FXML
    private Label dataEvento;
    @FXML
    private Label partecipazioniEvento;
    @FXML
    private TextField nomeEvento;
    @FXML
    private TextArea descrizioneEvento;
    @FXML
    private TextField pswCittadino;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;
    @FXML
    private CheckBox confermaEliminazione;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBroadcast;

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

        codiceEvento.setText(controller.getEventoSelezionato().getCodice());
        dataEvento.setText(controller.getEventoSelezionato().getData());
        int numPartecipazioni = controller.getNumBigliettiPrenotatiEvento();
        partecipazioniEvento.setText(String.valueOf(numPartecipazioni));
        nomeEvento.setText(controller.getEventoSelezionato().getNome());
        descrizioneEvento.setText(controller.getEventoSelezionato().getDescrizione());

        btnConfirm.setOnAction(e -> {
            try {
                System.out.println("Modifica evento");
                if(nomeEvento.getText().isEmpty() || descrizioneEvento.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Compilare tutti i campi");
                    alert.showAndWait();
                }else{
                    String[] dataSplit = dataEvento.getText().split("/");
                    String data_usa = dataSplit[2] + "-" + dataSplit[1] + "-" + dataSplit[0];
                    if(controller.onUpdateEvento(controller.getEventoSelezionato().getId(), controller.getEventoSelezionato().getCodice(), nomeEvento.getText(), data_usa, descrizioneEvento.getText())){
                        controller.setViewAttuale(new ListaEventi(controller, stage));
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante la modifica dell'evento");
                        alert.showAndWait();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnCancel.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaEventi(controller, stage));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnDelete.setOnAction(e -> {
            if(confermaEliminazione.isSelected()){
                try {
                    System.out.println("Eliminazione evento");
                    if(controller.onDeleteEvento(controller.getEventoSelezionato().getId())){
                        controller.setViewAttuale(new ListaEventi(controller, stage));
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Errore durante l'eliminazione dell'evento");
                        alert.showAndWait();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Confermare l'eliminazione dell'evento");
                alert.showAndWait();
            }
        });

        btnBroadcast.setOnAction(e -> {
            try {
                if(numPartecipazioni > 0){
                    controller.setViewAttuale(new InviaEmail(controller, stage));
                }else{
                    controller.alert(Alert.AlertType.WARNING, "Nessun cittadino partecipa all'evento");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public ModificaEvento(Controller c, Stage stage) {
        super(c, "Modifica Evento", "fxml/ModificaEvento.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Modifica evento");
        }
    }
}
