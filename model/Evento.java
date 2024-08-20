package model;

public class Evento {
    private int id;
    private String codice;
    private String nome;
    private String data;
    private String descrizione;
    private int posti;

    public Evento(String codice, String nome, String data, String descrizione) {
        this.codice = codice;
        this.nome = nome;
        this.data = data;
        this.descrizione = descrizione;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getPosti() {
        return posti;
    }

    public void setPosti(int posti) {
        this.posti = posti;
    }

    @Override
    public String toString() {
        return this.nome + " | " + this.data;
    }
}
