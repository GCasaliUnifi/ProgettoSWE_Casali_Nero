package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Registrazione extends ViewInterface {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField cellphone;
    @FXML
    private TextField email;
    @FXML
    private PasswordField psw;
    @FXML
    private PasswordField pswConfirm;
    @FXML
    private Button conferma;
    @FXML
    private Button accedi;


    public Registrazione(Controller c, Stage stage) {
        super(c, "Registrazione", "fxml/SignUp.fxml");
        ViewInterface.stage = stage;
    }

    @FXML
    public void initialize() {
        accedi.setOnAction(event -> {
            try {
                controller.setViewAttuale(new LogIn(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        conferma.setOnAction(event -> {
            if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || cellphone.getText().isEmpty() || email.getText().isEmpty()
                    || psw.getText().isEmpty() || pswConfirm.getText().isEmpty()) {
                System.out.println("Errore: uno o più campi sono vuoti!");
            } else {
                if (!psw.getText().equals(pswConfirm.getText())) {
                    System.out.println("Le password non coincidono.");
                } else {
                    try {
                        controller.onRegister(firstName.getText(), lastName.getText(), cellphone.getText(), email.getText(), psw.getText());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
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
            throw new IOException("Errore nel caricamento della finestra di registrazione");
        }
    }
}
