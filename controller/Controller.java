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
    private Stage stage;

    public Controller(Stage stage) throws Exception {
        this.utente = null;
        this.stage = stage;
        setViewAttuale(new LogIn(this, stage));
    }

    public void setViewAttuale(ViewInterface view) throws Exception {
        this.viewAttuale = view;
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

    public void onRegister(String firstName, String lastName, String cellphone, String email, String passwd) {
        utente = new Utente(email, passwd);
        utente.setNome(firstName);
        utente.setCognome(lastName);
        utente.setTelefono(cellphone);
        UtenteDAO utenteDAO = new UtenteDAO(this.utente);
        if(utenteDAO.tryRegister()) {
            System.out.println("Registrazione effettuata con successo!");
            try {
                this.setViewAttuale(new LogIn(this, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Errore: mail già presente, usare un'altra mail!");
        }
    }
}
