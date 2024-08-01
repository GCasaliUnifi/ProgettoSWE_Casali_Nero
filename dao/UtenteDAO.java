package dao;

import model.Padiglione;
import model.Utente;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UtenteDAO {
    boolean createUtente(Utente utente) throws SQLException;
    Utente readUtente(int id) throws SQLException;
    Utente readUtente(String email) throws SQLException;
    ArrayList<Utente> readAllCittadini() throws SQLException;
    boolean updateUtente(Utente utente) throws SQLException;
    boolean deleteUtente(int id) throws SQLException;
}
