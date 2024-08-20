package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Evento;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class DettagliBiglietto extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private TextField nomeEvento;
    @FXML
    private DatePicker dataEvento;
    @FXML
    private TextArea descrizioneEvento;
    @FXML
    private TextField nomeIntestatario;
    @FXML
    private TextField cognomeIntestatario;
    @FXML
    private TextField codfIntestatario;
    @FXML
    private DatePicker dataIntestatario;

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

        Evento evento = controller.getEvento(controller.getBigliettoSelezionato().getIdEvento());
        nomeEvento.setText(evento.getNome());
        dataEvento.setValue(Date.valueOf(evento.getData()).toLocalDate());
        descrizioneEvento.setText(evento.getDescrizione());

        nomeIntestatario.setText(controller.getBigliettoSelezionato().getNome());
        cognomeIntestatario.setText(controller.getBigliettoSelezionato().getCognome());
        codfIntestatario.setText(controller.getBigliettoSelezionato().getCodiceFiscale());
        dataIntestatario.setValue(Date.valueOf(controller.getBigliettoSelezionato().getDataPrenotazione()).toLocalDate());

    }

    public DettagliBiglietto(Controller c, Stage stage) {
        super(c, "Il Tuo Biglietto", "fxml/DettagliBiglietto.fxml");
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
            throw new IOException("Errore nel caricamento della finestra dei DettagliBiglietto: "+e.getMessage());
        }
    }
}
