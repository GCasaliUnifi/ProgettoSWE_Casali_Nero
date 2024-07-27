package controller;

import dao.UtenteDAOImpl;
import dao.UtenteDAO;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Utente;
import view.Home;
import view.LogIn;
import view.ViewInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
    private Utente utente;
    private ViewInterface viewAttuale;
    private Stage stage;

    public Controller(Stage stage) throws Exception {
        this.utente = null;
        this.stage = stage;
        setViewAttuale(new LogIn(this, stage));
    }

    private String getSecurePassword(String clearPsw) {
        String generatedPsw = null;
        String salt = "A1B2C3D4E5F6G7H8I9J0";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(clearPsw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPsw = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPsw;
    }

    private void alert(AlertType type,String message) {
        Alert warningAlert = new Alert(type, message);
        warningAlert.setTitle("Attenzione!");
        warningAlert.setHeaderText(null);
        warningAlert.showAndWait();
    }

    public void setViewAttuale(ViewInterface view) throws Exception {
        this.viewAttuale = view;
        try {
            viewAttuale.display();
        } catch (IOException e) {
            throw new Exception("Errore nel caricamento della finestra");
        }
    }

    public void onLogin(String email, String password) throws SQLException {
        utente = new Utente(email, getSecurePassword(password));
        UtenteDAO utenteDAO = new UtenteDAOImpl(this.utente);
        Utente u = utenteDAO.readUtente(email);
        if(u != null) {
            if(u.getPassword().equals(utente.getPassword())) {
                System.out.println("Login effettuato!");
                this.utente = u;
                try {
                    this.setViewAttuale(new Home(this, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Email o password errati!");
                this.alert(AlertType.WARNING,"Email o password errati!");
            }
        } else {
            System.out.println("Email o password errati!");
            this.alert(AlertType.WARNING, "Email o password errati!");
        }
    }

    public void onRegister(String firstName, String lastName, String cellphone, String email, String passwd) throws SQLException {
        utente = new Utente(email, getSecurePassword(passwd));
        UtenteDAO utenteDAO = new UtenteDAOImpl(this.utente);
        Utente u = utenteDAO.readUtente(email);
        if(u == null) {
            utente.setNome(firstName);
            utente.setCognome(lastName);
            utente.setTelefono(cellphone);
            utente.setType(0);

            if(utenteDAO.createUtente(this.utente)) {
                System.out.println("Registrazione effettuata con successo!");
                try {
                    this.setViewAttuale(new LogIn(this, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("Errore: mail già presente, usare un'altra mail!");
            this.alert(AlertType.ERROR, "Errore: mail già presente, usare un'altra mail!");
        }
    }

    public boolean isAmministratore() {
        return (utente.getType() == 1);
    }
}
