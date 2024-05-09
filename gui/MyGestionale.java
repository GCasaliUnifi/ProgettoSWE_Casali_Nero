package gui;

import com.sun.tools.javac.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class MyGestionale extends Application {
    private static Stage primaryStage;
    private Parent root = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyGestionale.primaryStage = primaryStage;
        showSignInScreen();
    }

    private <T extends Controller> void buildScene(T controller, String fxmlPath, String title, int width, int height) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        root = loader.load();
        controller = loader.getController();
        controller.setMyGestionale(this);
        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setTitle(title);
    }

    public void showSignInScreen() throws Exception {
        String fxmlPath = "fxml/SignIn.fxml";
        String title = "Gestionale Eventi - Accedi";
        buildScene(new SignInController(), fxmlPath, title, 600, 400);
        primaryStage.show();
    }

    public void showSignUpScreen() throws Exception {
        String fxmlPath = "fxml/SignUp.fxml";
        String title = "Gestionale Eventi - Registrati";
        buildScene(new SignUpController(), fxmlPath, title, 600, 400);
    }
}
