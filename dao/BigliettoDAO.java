package dao;

import model.Biglietto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BigliettoDAO {
    boolean createBiglietto(Biglietto evento) throws SQLException;
    Biglietto readBiglietto(int id) throws SQLException;
    ArrayList<Biglietto> readAllBigliettiEvento(int id_evento) throws SQLException;
    ArrayList<Biglietto> readDistinctAllBigliettiEvento(int id_evento) throws SQLException;
    ArrayList<Biglietto> readAllBigliettiUtente(int id_utente) throws SQLException;
    int readBigliettoUtenteEvento(int id_utente, int id_evento) throws SQLException;
    boolean updateBiglietto(Biglietto evento) throws SQLException;
    boolean deleteBiglietto(int id) throws SQLException;
}
