package view;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AggiungiPadiglione extends ViewInterface{
    public AggiungiPadiglione(Controller c, Stage stage) {
        super(c, "Dashboard", "fxml/AggiungiPadiglione.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di aggiunta padiglione");
        }
    }
}
