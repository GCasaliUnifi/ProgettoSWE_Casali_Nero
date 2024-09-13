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

public class InviaEmail  extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private TextField oggettoEmail;
    @FXML
    private TextArea descrizioneEmail;
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

        btnCancel.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ModificaEvento(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        btnConfirm.setOnAction(e -> {
            try {
                if(oggettoEmail.getText().isEmpty() || descrizioneEmail.getText().isEmpty()){
                    controller.alert(Alert.AlertType.WARNING, "Inserire tutti i campi");
                }else{
                    if(controller.inviaEmailBroadcast(oggettoEmail.getText(), descrizioneEmail.getText())) {
                        controller.alert(Alert.AlertType.INFORMATION, "Email inviate con successo");
                        controller.setViewAttuale(new ModificaEvento(controller, stage));
                    }else{
                        controller.alert(Alert.AlertType.ERROR, "Errore nell'invio delle email");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public InviaEmail(Controller c, Stage stage) {
        super(c, "Invia Email", "fxml/InviaEmail.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Invia email");
        }
    }
}
