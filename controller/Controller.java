package controller;

import javafx.stage.Stage;
import model.Utente;
import view.LogIn;
import view.ViewInterface;

import java.io.IOException;

public class Controller {
    private Utente utente;
    private ViewInterface viewAttuale;

    public Controller(Stage stage) throws Exception {
        this.utente = null;
        this.viewAttuale = new LogIn(this, stage);
        try {
            viewAttuale.display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
