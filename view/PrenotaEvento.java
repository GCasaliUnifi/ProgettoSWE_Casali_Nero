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

import java.io.IOException;
import java.sql.SQLException;

public class PrenotaEvento extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private ImageView imgEvento;
    @FXML
    private TextField nomeEvento;
    @FXML
    private TextField dataEvento;
    @FXML
    private TextArea descrizioneEvento;
    @FXML
    private TextField bigliettiDisponibili;
    @FXML
    private Button btnPrenota;

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

        try {
            Image image = new Image("img/sagra_italiana.jpg");
            imgEvento.setImage(image);
            imgEvento.setFitWidth(250);
            imgEvento.setPreserveRatio(true);
        } catch (Exception e) {
            System.out.println("Errore nel caricamento dell'immagine: " + e.getMessage());
        }
        nomeEvento.setText(controller.getEventoSelezionato().getNome());
        dataEvento.setText(controller.getEventoSelezionato().getData());
        descrizioneEvento.setText(controller.getEventoSelezionato().getDescrizione());
        Evento evento = controller.getEvento(controller.getEventoSelezionato().getId());
        bigliettiDisponibili.setText(String.valueOf(evento.getPosti()));

        btnPrenota.setOnAction(e -> {
            try {
                System.out.println("Prenota evento");

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }

    public PrenotaEvento(Controller c, Stage stage) {
        super(c, "Prenota Evento", "fxml/PrenotaEvento.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Prenotazione evento: "+e.getMessage());
        }
    }
}
