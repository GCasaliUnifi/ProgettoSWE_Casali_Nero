package controller;

import dao.*;
import javafx.stage.Stage;
import model.*;
import view.Home;
import view.LogIn;
import view.ViewInterface;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

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

    //PADIGLIONE

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

    //EVENTO

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

    public boolean onUpdateEvento(int id, String codice, String nome, String data, String descrizione) throws SQLException {
        Evento evento = new Evento(codice, nome, data, descrizione);
        evento.setId(id);
        EventoDAO eventoDAO = new EventoDAOImpl();

        if(eventoDAO.updateEvento(evento)) {
            System.out.println("Evento aggiornato con successo!");
            return true;
        }else{
            return false;
        }
    }

    public boolean onDeleteEvento(int id) throws SQLException {
        EventoDAO eventoDAO = new EventoDAOImpl();

        if(eventoDAO.deleteEvento(id)) {
            System.out.println("Evento eliminato con successo!");
            return true;
        }else{
            return false;
        }
    }

    //UTENTE

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

    public Utente getUtente(int id_utente) throws SQLException {
        UtenteDAO utenteDAO = new UtenteDAOImpl(this.utente);
        return utenteDAO.readUtente(id_utente);
    }

    public boolean onUpdateUtente(int id, String nome, String cognome, String email, String telefono, String password, boolean type) throws SQLException {
        UtenteDAO utenteDAO = new UtenteDAOImpl(this.utente);
        Utente u = utenteDAO.readUtente(id);
        String newPsw = password;
        if(u.getPassword().equals(newPsw)){
            newPsw = u.getPassword();
        }else{
            newPsw = getSecurePassword(password);
        }
        u = utenteDAO.readUtente(email);

        if(u == null || u.getId() == id) {
            Utente utente = new Utente(email, newPsw);
            utente.setId(id);
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setTelefono(telefono);
            if(type){
                utente.setType(1);
            }else{
                utente.setType(0);
            }
            if(utenteDAO.updateUtente(utente)) {
                System.out.println("Utente aggiornato con successo!");
                setUtenteSelezionato(utente);
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("Errore: mail già presente, usare un'altra mail!");
            this.alert(AlertType.ERROR, "Errore: email già utilizzata!");
            return false;
        }
    }

    //LICENZA

    public Licenza getLicenzaCittadino() throws SQLException {
        LicenzaDAO licenzaDAO = new LicenzaDAOImpl();
        return licenzaDAO.readLicenza(this.utente.getId());
    }

    public Licenza getLicenzaCittadino(int id_utente) throws SQLException {
        LicenzaDAO licenzaDAO = new LicenzaDAOImpl();
        return licenzaDAO.readLicenza(id_utente);
    }

    public boolean onAddLicenza(int id_utente, String codice, String scadenza) throws SQLException {
        Licenza licenza = new Licenza(codice);
        licenza.setId_utente(id_utente);
        licenza.setScadenza(scadenza);
        LicenzaDAO licenzaDAO = new LicenzaDAOImpl();
        Licenza l = licenzaDAO.readLicenza(codice);

        if(l == null) {
            if(licenzaDAO.createLicenza(licenza)) {
                removeNotificaLicenzaCittadino();
                System.out.println("Licenza aggiunta con successo!");
                return true;
            }
        } else {
            System.out.println("Errore: codice licenza già presente!");
            return false;
        }
        return false;
    }

    public boolean onDeleteLicenza(int id) throws SQLException {
        LicenzaDAO licenzaDAO = new LicenzaDAOImpl();
        if(licenzaDAO.deleteLicenza(id)) {
            System.out.println("Licenza eliminata con successo!");
            return true;
        } else {
            return false;
        }
    }

    public boolean onUpdateLicenza(int id, int id_utente, String codice, String scadenza) throws SQLException {
        Licenza licenza = new Licenza(codice);
        licenza.setId_utente(id_utente);
        licenza.setScadenza(scadenza);
        licenza.setId(id);
        LicenzaDAO licenzaDAO = new LicenzaDAOImpl();
        if(licenzaDAO.updateLicenza(licenza)) {
            removeNotificaLicenzaCittadino();
            System.out.println("Licenza aggiornata con successo!");
            return true;
        } else {
            return false;
        }
    }

    public boolean onRequestLicenza() throws SQLException {
        Notifica notifica = new Notifica(0, utente.getId(), null);
        NotificaDAO notificaDAO = new NotificaDAOImpl(notifica);
        ArrayList<Notifica> n = notificaDAO.readNotifiche(utente.getId());
        if(n != null) {
            for(Notifica not : n) {
                if(not.getTipo() == 0 && not.getStato() == 0) {
                    System.out.println("Richiesta licenza già inviata!");
                    this.alert(AlertType.WARNING, "Richiesta licenza già inviata!");
                    return false;
                }
            }
        }

        if(notificaDAO.createNotifica(notifica)) {
            System.out.println("Richiesta licenza inviata con successo!");
            this.alert(AlertType.INFORMATION, "Richiesta licenza inviata con successo!");
            return true;
        }else{
            return false;
        }
    }

    public boolean isLicenzaScaduta(Licenza licenza) {
        String[] dataScadenza = licenza.getScadenza().split("-");
        int anno = Integer.parseInt(dataScadenza[0]);
        int mese = Integer.parseInt(dataScadenza[1]);
        int giorno = Integer.parseInt(dataScadenza[2]);
        java.util.Date data = new java.util.Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(data);
        int annoAttuale = cal.get(java.util.Calendar.YEAR);
        int meseAttuale = cal.get(java.util.Calendar.MONTH) + 1;
        int giornoAttuale = cal.get(java.util.Calendar.DAY_OF_MONTH);
        if(anno < annoAttuale) {
            return true;
        } else if(anno == annoAttuale) {
            if(mese < meseAttuale) {
                return true;
            } else if(mese == meseAttuale) {
                if(giorno < giornoAttuale) {
                    return true;
                }
            }
        }
        return false;
    }

    //NOTIFICA
    public ArrayList<Notifica> getNotificheLicenza() throws SQLException {
        NotificaDAO notificaDAO = new NotificaDAOImpl(new Notifica(0, 0, null));
        ArrayList<Notifica> lista;

        lista = notificaDAO.readAllNotifiche();
        if(!lista.isEmpty()) {
            //crea una lista di notifiche di tipo 0
            ArrayList<Notifica> listaLicenze = new ArrayList<Notifica>();
            for(Notifica n : lista) {
                if(n.getTipo() == 0 && n.getStato() == 0) {
                    listaLicenze.add(n);
                }
            }
            return listaLicenze;
        } else {
            return null;
        }
    }

    public void removeNotificaLicenzaCittadino(){
        //recupera le notifiche di tipo 0 del cittadino selezionato
        NotificaDAO notificaDAO = new NotificaDAOImpl(new Notifica(0, 0, null));
        ArrayList<Notifica> lista;
        try {
            lista = notificaDAO.readNotifiche(utenteSelezionato.getId());
            if(!lista.isEmpty()) {
                for(Notifica n : lista) {
                    if(n.getTipo() == 0) {
                        notificaDAO.deleteNotifica(n.getId());
                    }
                }
            }else{
                System.out.println("Nessuna notifica da eliminare");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
