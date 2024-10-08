package view;

import javafx.scene.Parent;
import javafx.stage.Stage;

import controller.Controller;

import java.io.IOException;

public abstract class ViewInterface {
    protected Controller controller;
    protected String title;
    protected String fxmlPath;
    protected int width = 640;
    protected int height = 400;

    protected static Stage stage;
    protected Parent root = null;

    public ViewInterface(Controller c, String title, String fxmlPath) {
        this.controller = c;
        this.title = title;
        this.fxmlPath = fxmlPath;
    }

    public abstract void display() throws IOException;
}
