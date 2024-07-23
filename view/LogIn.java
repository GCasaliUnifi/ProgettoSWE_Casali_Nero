package view;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class LogIn extends ViewInterface {

    public LogIn(Controller c) {
        super(c, "LogIN", "fxml/SignIn.fxml");
    }

    @Override
    public void display() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        root = loader.load();
        stage.setScene(new Scene(root, width, height));
        stage.setTitle(title);
        stage.show();
    }
}
