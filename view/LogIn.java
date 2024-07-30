package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LogIn extends ViewInterface {

    @FXML
    private TextField email;
    @FXML
    private TextField psw;
    @FXML
    private Button accedi;
    @FXML
    private Button registrati;

    @FXML
    public void initialize() {
        accedi.setOnAction(event -> {
            if(email.getText().isEmpty() || psw.getText().isEmpty()) {
                System.out.println("Errore: uno o più campi sono vuoti!");
            } else {
                try {
                    controller.onLogin(email.getText(), psw.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        registrati.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Registrazione(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        psw.setOnKeyPressed(event -> {  // Se premo invio mentre sono nel campo password, simula il click del bottone accedi
            if (event.getCode() == KeyCode.ENTER) {
                accedi.fire();
            }
        });
    }

    public LogIn(Controller c, Stage stage) {
        super(c, "LogIn", "fxml/SignIn.fxml");
        ViewInterface.stage = stage;
    }

    @Override
    public void display() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(this);
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            throw new IOException("Errore nel caricamento della finestra di login");
        }
    }
}
