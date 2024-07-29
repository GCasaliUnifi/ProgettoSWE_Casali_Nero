package model;

public class Padiglione {
    private int id;
    private String codice;
    private float dimensione;

    public Padiglione(String codice, float dimensione) {
        this.codice = codice;
        this.dimensione = dimensione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public float getDimensione() {
        return dimensione;
    }

    public void setDimensione(float dimensione) {
        this.dimensione = dimensione;
    }
}
