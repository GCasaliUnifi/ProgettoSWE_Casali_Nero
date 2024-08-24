package model;

public class PadiglioneEvento {
    private int id_evento;
    private int id_padiglione;
    private int id_utente;
    private int tipo;
    private String nomeEvento;
    private String codicePadiglione;
    private String tipoPadiglione;

    public PadiglioneEvento(int id_evento, int id_padiglione, int id_utente, int tipo) {
        this.id_evento = id_evento;
        this.id_padiglione = id_padiglione;
        this.id_utente = id_utente;
        this.tipo = tipo;
        this.tipoPadiglione = switch (tipo) {
            case 0 -> "Ristoro";
            case 1 -> "Commerciale";
            case 2 -> "Intrattenimento";
            default -> "Altro...";
        };
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public int getId_padiglione() {
        return id_padiglione;
    }

    public void setId_padiglione(int id_padiglione) {
        this.id_padiglione = id_padiglione;
    }

    public int getId_utente() {
        return id_utente;
    }

    public void setId_utente(int id_utente) {
        this.id_utente = id_utente;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
        this.tipoPadiglione = switch (tipo) {
            case 0 -> "Ristoro";
            case 1 -> "Commerciale";
            case 2 -> "Intrattenimento";
            default -> "Altro...";
        };
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getCodicePadiglione() {
        return codicePadiglione;
    }

    public void setCodicePadiglione(String codicePadiglione) {
        this.codicePadiglione = codicePadiglione;
    }

    public String getTipoPadiglione() {
        return tipoPadiglione;
    }

    public void setTipoPadiglione(String tipoPadiglione) {
        this.tipoPadiglione = tipoPadiglione;
    }
}
