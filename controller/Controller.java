package controller;

import model.Utente;
import view.LogIn;
import view.ViewInterface;

import java.io.IOException;

public class Controller {
    private Utente utente;
    private ViewInterface viewAttuale;

    public Controller() throws Exception {
        this.utente = null;
        this.viewAttuale = new LogIn(this);
        try {
            viewAttuale.display();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        this.viewAttuale = new LogIn(this);
    }
}
