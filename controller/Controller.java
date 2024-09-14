package controller;

import dao.*;
import javafx.stage.Stage;
import model.*;
import view.Home;
import view.LogIn;
import view.ViewInterface;
import sender.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Controller {
    private Utente utente;
    private ViewInterface viewAttuale;
    private Stage stage;
    private Padiglione padiglioneSelezionato;
    private Evento eventoSelezionato;
    private Utente utenteSelezionato;
    private Biglietto bigliettoSelezionato;

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

    public void setBigliettoSelezionato(Biglietto biglietto) {
        this.bigliettoSelezionato = biglietto;
    }

    public Biglietto getBigliettoSelezionato() {
        return this.bigliettoSelezionato;
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
            System.out.println("Errore nella visualizzazione della finestra: " + e.getMessage());
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

    public ArrayList<Evento> getListaEventiDisponibili(int idPadiglione) throws SQLException {
        EventoDAO eventoDAO = new EventoDAOImpl();
        ArrayList<Evento> lista;
        lista = eventoDAO.readAllEventi(idPadiglione);

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

    public Evento getEvento(int id) throws SQLException {
        EventoDAO eventoDAO = new EventoDAOImpl();
        return eventoDAO.readEvento(id);
    }

    public String generaCampo(int codiceCampo){
        String campo = "";
        String[] valori = switch (codiceCampo) {
            case 0 -> //Ritornerà un nome casuale
                    new String[]{"Marco", "Luca", "Andrea", "Matteo", "Giovanni", "Alessandro", "Davide", "Michele", "Lorenzo", "Francesco", "Riccardo", "Simone", "Antonio", "Fabio", "Giuseppe", "Paolo", "Federico", "Stefano", "Alberto", "Claudio", "Roberto", "Giorgio", "Massimo", "Gianluca", "Gianmarco", "Gianfranco", "Giancarlo", "Gianpaolo", "Gianandrea", "Gianantonio", "Gianmichele", "Gianfrancesco", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio", "Gianroberto", "Giangiuseppe", "Gianpaolo", "Gianfederico", "Gianstefano", "Gianalberto", "Gianclaudio"};
            case 1 -> //Ritornerà un cognome casuale
                //cognomi italiani più comuni
                    new String[]{"Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Colombo", "Ricci", "Marino", "Greco", "Bruno", "Gallo", "Conti", "De Luca", "Mancini", "Costa", "Giordano", "Rizzo", "Lombardi", "Moretti", "Barbieri", "Fontana", "Santoro", "Mariani", "Rinaldi", "Caruso", "Ferrara", "Galli", "Martini", "Leone", "Longo", "Gentile", "Martinelli", "Vitale", "Lombardo", "Serra", "Coppola", "De Santis", "D'Angelo", "Marchetti", "Parisi", "Villa", "Conte", "Ferraro", "Ferri", "Fabbri", "Bianco", "Marini", "Grasso", "Valentini", "Messina", "Sala", "De Angelis", "Gatti", "Pellegrini", "Palumbo", "Sanna", "Farina", "Rizzi", "Monti", "Cattaneo", "Morelli", "Amato", "Silvestri", "Mazza", "Testa", "Grassi", "Pellegrino", "Carbone", "Giuliani", "Benedetti", "Barone", "Rossetti", "Caputo", "Montanari", "Guerra", "Palmieri", "Bernardi", "Martino", "Fiore", "De Rosa", "Ferretti", "Bellini", "Basile", "Riva", "Donati", "Piras", "Vitali", "Battaglia", "Sartori", "Neri", "Costantini", "Milani", "Pagano", "Ruggiero", "Sorrentino", "D'Amico", "Orlando", "Damico", "Negri", "Poli", "D'Agostino", "Fioretti", "Coppola", "Piras", "Vitali", "Battaglia", "Sartori", "Neri", "Costantini", "Milani"};
            case 2 -> //Ritornerà un codice fiscale casuale
                    new String[]{"RSSMRC97A01A007D", "GLLSRN96R49A132H", "FRRMRA97A01A007D", "SPTGPP97A01A007D", "BNCBNC97A01A007D", "RMMRNN97A01A007D", "CLMBBR97A01A007D", "RCCMRC97A01A007D", "MRRNMR97A01A007D", "GRCGRC97A01A007D", "BRNBRN97A01A007D", "GLLGRC97A01A007D", "CNTCNT97A01A007D", "DLCLCC97A01A007D", "MNCCST97A01A007D", "CSTCST97A01A007D", "GRDNDR97A01A007D", "RZZRZZ97A01A007D", "LMBRDI97A01A007D", "MRTMRT97A01A007D", "LNGLMB97A01A007D", "MRTNLL97A01A007D", "BRRBRR97A01A007D", "FNTFNT97A01A007D", "SNTRSN97A01A007D", "MRNMRN97A01A007D", "RLNRLN97A01A007D", "CRSCRS97A01A007D", "FRRFRR97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT87N12A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D", "MRTMRT97A01A007D", "LMBRDO97A01A007D", "LNGGNT97A01A007D", "GNTGNT97A01A007D"};
            default -> null;
        };
        if(valori != null){
            //genera un numero casuale tra 0 e la lunghezza di valori
            int random = (int) (Math.random() * valori.length);
            //assegna a campo il valore casuale
            campo = valori[random];
        }
        return campo;
    }

    public void onPrenotaEvento(String nome, String cognome, String codf) throws SQLException {
        Biglietto biglietto = new Biglietto(nome, cognome, codf);
        BigliettoDAO bigliettoDAO = new BigliettoDAOImpl();
        if(bigliettoDAO.readBigliettoUtenteEvento(utente.getId(), eventoSelezionato.getId()) >= 2) {
            System.out.println("Questo utente possiede già 2 biglietti per questo evento");
            this.alert(AlertType.ERROR, "Non puoi acquistare più di 2 biglietti per questo evento");
            return;
        }
        biglietto.setIdUtente(utente.getId());
        biglietto.setIdEvento(eventoSelezionato.getId());
        if(bigliettoDAO.createBiglietto(biglietto)) {
            System.out.println("Prenotazione effettuata con successo!");
            this.alert(AlertType.CONFIRMATION, "Prenotazione effettuata con successo!");
        } else {
            this.alert(AlertType.ERROR, "I biglietti disponibili per questo evento sono terminati");
        }
    }

    public int getNumBigliettiPrenotatiEvento() throws SQLException {
        BigliettoDAO bigliettoDAO = new BigliettoDAOImpl();
        return bigliettoDAO.readAllBigliettiEvento(eventoSelezionato.getId()).size();
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
            ArrayList<Notifica> listaLicenze = new ArrayList<>();
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

    //CITTADINO
    public ArrayList<Biglietto> getBigliettiCittadino() throws SQLException {
        BigliettoDAO bigliettoDAO = new BigliettoDAOImpl();
        ArrayList<Biglietto> lista;
        lista = bigliettoDAO.readAllBigliettiUtente(utente.getId());

        if(!lista.isEmpty()) {
            return lista;
        } else {
            return null;
        }
    }

    public boolean onPrenotaPadiglione(int id_evento, int tipo) throws SQLException{
        PadiglioneEventoDAO padiglioneEventoDAO = new PadiglioneEventoDAOImpl();
        if(padiglioneEventoDAO.createPadiglioneEvento(id_evento, padiglioneSelezionato.getId(), utente.getId(), tipo)) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<PadiglioneEvento> getPadiglioniEvento_Cittadino() throws SQLException {
        PadiglioneEventoDAO padiglioneEventoDAO = new PadiglioneEventoDAOImpl();
        ArrayList<PadiglioneEvento> lista;
        lista = padiglioneEventoDAO.readAllPadiglioniEvento(utente.getId());

        if(!lista.isEmpty()) {
            return lista;
        } else {
            return null;
        }
    }

    public void stampaBigliettoIngresso() throws Exception {
        if(this.bigliettoSelezionato.generaPDF()){
            this.alert(AlertType.INFORMATION, "Biglietto generato con successo!");
        }else{
            this.alert(AlertType.ERROR, "Errore nella generazione del biglietto!");
        }
    }

    //IC
    public void onVerifyBiglietto(String codice) throws SQLException {
        String[] codici = codice.split("-");
        if(codici.length != 2) {
            System.out.println("Codice biglietto non valido!");
            this.alert(AlertType.ERROR, "Codice biglietto non valido!");
        }else{
            int id_evento = Integer.parseInt(codici[0]);
            String codf = codici[1];
            BigliettoDAO bigliettoDAO = new BigliettoDAOImpl();
            Biglietto biglietto = bigliettoDAO.readBiglietto(id_evento);
            if(biglietto != null) {
                if(Objects.equals(biglietto.getCodiceFiscale().toUpperCase(), codf.toUpperCase())) {
                    System.out.println("Biglietto valido!");
                    this.alert(AlertType.INFORMATION, "Biglietto valido!");
                } else {
                    System.out.println("Biglietto non valido per questo evento!");
                    this.alert(AlertType.ERROR, "Biglietto non valido per questo evento!");
                }
            } else {
                System.out.println("Biglietto non valido!");
                this.alert(AlertType.ERROR, "Biglietto non valido!");
            }
        }
    }

    public boolean inviaEmailBroadcast(String oggetto, String messaggio) {

        //recupera la lista di tutti i biglietti prenotati per l'evento selezionato
        BigliettoDAO bigliettoDAO = new BigliettoDAOImpl();
        ArrayList<Biglietto> lista;

        try {
            lista = bigliettoDAO.readDistinctAllBigliettiEvento(eventoSelezionato.getId());
            if(!lista.isEmpty()) {
                for(Biglietto b : lista) {
                    //recupera l'utente associato al biglietto
                    UtenteDAO utenteDAO = new UtenteDAOImpl(this.utente);
                    Utente u = utenteDAO.readUtente(b.getIdUtente());
                    //invia l'email all'utente
                    Email email = new Email.EmailBuilder().setTo(u.getEmail()).setSubject(oggetto).setBody(messaggio).build();
                    EmailService emailService = SMTPEmailService.getInstance();
                    emailService.sendEmail(email);
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
