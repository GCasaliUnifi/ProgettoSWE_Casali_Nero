package main;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewInterface;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller(primaryStage);
    }
}
