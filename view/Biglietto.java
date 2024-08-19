package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Evento;

import java.io.IOException;
import java.sql.SQLException;

public class Biglietto extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private TextField nomeBiglietto;
    @FXML
    private TextField cognomeBiglietto;
    @FXML
    private TextField codfBiglietto;
    @FXML
    private Button btnPrenota;
    @FXML
    private Button btnGenera;
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

        btnPrenota.setOnAction(e -> {
            try {
                if(!nomeBiglietto.getText().isEmpty() && !cognomeBiglietto.getText().isEmpty() && !codfBiglietto.getText().isEmpty()){
                    controller.onPrenotaEvento(nomeBiglietto.getText(), cognomeBiglietto.getText(), codfBiglietto.getText());
                    controller.setViewAttuale(new ListaEventi(controller, stage));
                }else{
                    controller.alert(Alert.AlertType.ERROR, "Inserire tutti i campi");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnGenera.setOnAction(e -> {
            nomeBiglietto.setText(controller.generaCampo(0));
            cognomeBiglietto.setText(controller.generaCampo(1));
            codfBiglietto.setText(controller.generaCampo(2));
        });

        btnCancel.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaEventi(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public Biglietto(Controller c, Stage stage) {
        super(c, "Biglietto", "fxml/Biglietto.fxml");
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
            throw new IOException("Errore nel caricamento della finestra del Biglietto: "+e.getMessage());
        }
    }
}
