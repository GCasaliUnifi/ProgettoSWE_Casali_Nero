package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AggiungiEvento extends ViewInterface {
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private TextField codiceEvento;
    @FXML
    private TextField nomeEvento;
    @FXML
    private DatePicker dataEvento;
    @FXML
    private TextArea descrizioneEvento;

    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;

    @FXML
    public void initialize() {
        menuHome.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        menuEventi.setOnAction(event -> {
            try {
                controller.setViewAttuale(new ListaEventi(controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        menuPadiglioni.setOnAction(event -> {
            try {
                controller.setViewAttuale(new ListaPadiglioni(controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnConfirm.setOnAction(event -> {
            if(codiceEvento.getText().isEmpty() || nomeEvento.getText().isEmpty() || descrizioneEvento.getText().isEmpty() || dataEvento.getValue() == null) {
                System.out.println("Errore: uno o più campi sono vuoti!");
                controller.alert(Alert.AlertType.WARNING, "Uno o più campi sono vuoti!");
            } else {
                if(dataEvento.getValue().isAfter(LocalDate.now())) {
                    try {
                        controller.onAddEvento(codiceEvento.getText(), nomeEvento.getText(), dataEvento.getValue().toString(), descrizioneEvento.getText());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    controller.alert(Alert.AlertType.ERROR, "Selezionare una data successiva ad oggi!");
                }
            }
        });

        btnCancel.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(this.controller, stage));
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AggiungiEvento(Controller c, Stage stage) {
        super(c, "Aggiungi Evento", "fxml/AggiungiEvento.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di aggiunta evento");
        }
    }
}
