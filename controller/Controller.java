package controller;

import dao.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Evento;
import model.Padiglione;
import model.Utente;
import view.Home;
import view.LogIn;
import view.ViewInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
    private Utente utente;
    private ViewInterface viewAttuale;
    private Stage stage;
    private Padiglione padiglioneSelezionato;
    private Evento eventoSelezionato;
    private Utente utenteSelezionato;

    public Controller(Stage stage) throws Exception {
        this.utente = null;
        this.stage = stage;
        setViewAttuale(new LogIn(this, stage));
    }

    public void setPadiglioneSelezionato(Padiglione padiglione) {
        this.padiglioneSelezionato = padiglione;
    }

    public Padiglione getPadiglioneSelezionato() {
        return this.padiglioneSelezionato;
    }

    public void setEventoSelezionato(Evento evento) {
        this.eventoSelezionato = evento;
    }

    public Evento getEventoSelezionato() {
        return this.eventoSelezionato;
    }

    public void setUtenteSelezionato(Utente utente) {
        this.utenteSelezionato = utente;
    }

    public Utente getUtenteSelezionato() {
        return this.utenteSelezionato;
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

    public void alert(AlertType type,String message) {
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

    public void onAddPadiglione(String codice, float dimensione) throws SQLException {
        Padiglione padiglione = new Padiglione(codice, dimensione);
        PadiglioneDAO padiglioneDAO = new PadiglioneDAOImpl();
        Padiglione p = padiglioneDAO.readPadiglione(codice);

        if(p == null) {
            if(padiglioneDAO.createPadiglione(padiglione)) {
                System.out.println("Padiglione aggiunto con successo!");
                try {
                    this.setViewAttuale(new Home(this, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("Errore: codice padiglione già presente!");
            this.alert(AlertType.ERROR, "Errore: codice padiglione già presente!");
        }
    }

    public ArrayList<Padiglione> getListaPadiglioni() throws SQLException {
        PadiglioneDAO padiglioneDAO = new PadiglioneDAOImpl();
        ArrayList<Padiglione> lista;
        lista = padiglioneDAO.readAllPadiglioni();

        if(!lista.isEmpty()) {
            return lista;
        } else {
            return null;
        }
    }

    public boolean onUpdatePadiglione(int id, String codice, float dimensione) throws SQLException{
        Padiglione padiglione = new Padiglione(codice, dimensione);
        padiglione.setId(id);
        PadiglioneDAO padiglioneDAO = new PadiglioneDAOImpl();
        Padiglione p = padiglioneDAO.readPadiglione(codice);
        if(p == null || p.getId() == id) {
            if(padiglioneDAO.updatePadiglione(padiglione)) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Errore: codice padiglione già presente!");
            this.alert(AlertType.ERROR, "Errore: codice padiglione già presente!");
            return false;
        }
    }

    public void onAddEvento(String codice, String nome, String data, String descrizione) throws SQLException {
        Evento evento = new Evento(codice, nome, data, descrizione);
        EventoDAO eventoDAO = new EventoDAOImpl();
        Evento ev = eventoDAO.readEvento(codice);

        if(ev == null) {
            if(eventoDAO.createEvento(evento)) {
                System.out.println("Evento aggiunto con successo!");
                try {
                    this.setViewAttuale(new Home(this, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            System.out.println("Errore: codice evento già presente!");
            this.alert(AlertType.ERROR, "Errore: codice evento già presente!");
        }
    }

    public ArrayList<Evento> getListaEventi() throws SQLException {
        EventoDAO eventoDAO = new EventoDAOImpl();
        ArrayList<Evento> lista;
        lista = eventoDAO.readAllEventi();

        if(!lista.isEmpty()) {
            return lista;
        } else {
            return null;
        }
    }

    public ArrayList<Utente> getListaCittadini() throws SQLException {
        UtenteDAO utenteDAO = new UtenteDAOImpl(this.utente);
        ArrayList<Utente> lista;
        lista = utenteDAO.readAllCittadini();

        if(!lista.isEmpty()) {
            return lista;
        } else {
            return null;
        }
    }
}
