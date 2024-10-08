package dao;


import model.Padiglione;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PadiglioneDAO {
    boolean createPadiglione(Padiglione padiglione) throws SQLException;
    Padiglione readPadiglione(int id) throws SQLException;
    Padiglione readPadiglione(String codice) throws SQLException;
    ArrayList<Padiglione> readAllPadiglioni() throws SQLException;
    boolean updatePadiglione(Padiglione padiglione) throws SQLException;
    boolean deletePadiglione(int id) throws SQLException;
}
