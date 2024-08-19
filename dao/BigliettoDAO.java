package dao;

import model.Biglietto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BigliettoDAO {
    boolean createBiglietto(Biglietto evento) throws SQLException;
    Biglietto readBiglietto(int id) throws SQLException;
    ArrayList<Biglietto> readAllBiglietti(int id_evento) throws SQLException;
    int readBigliettoUtenteEvento(int id_utente, int id_evento) throws SQLException;
    boolean updateBiglietto(Biglietto evento) throws SQLException;
    boolean deleteBiglietto(int id) throws SQLException;
}
