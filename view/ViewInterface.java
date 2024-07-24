package view;

import javafx.scene.Parent;
import javafx.stage.Stage;

import controller.Controller;

public class ViewInterface {
    protected Controller controller;
    protected String title;
    protected String fxmlPath;
    protected int width = 640;
    protected int height = 480;

    protected static Stage stage;
    protected Parent root = null;

    public ViewInterface(Controller c, String title, String fxmlPath) {
        this.controller = c;
        this.title = title;
        this.fxmlPath = fxmlPath;
    }

//    public static void init(String[] args) throws Exception {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        stage = primaryStage;
//
//    }

    public void display() throws Exception {}
}
