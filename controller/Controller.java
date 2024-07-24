package controller;

import dao.UtenteDAO;
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
            throw new Exception("Errore nel caricamento della finestra di login");
        }
    }

    public void onLogin(String email, String password) {
        utente = new Utente(email, password);
        UtenteDAO utenteDAO = new UtenteDAO(this.utente);
        if(utenteDAO.tryLogin()) {
            System.out.println("Login effettuato!");
        } else {
            System.out.println("Email o password errati!");
        }
    }
}
