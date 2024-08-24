package dao;

import model.PadiglioneEvento;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PadiglioneEventoDAO {
    boolean createPadiglioneEvento(int id_evento, int id_padiglione, int id_utente, int tipo) throws SQLException;
    boolean deletePadiglioneEvento(int id_evento, int id_padiglione, int id_utente) throws SQLException;
    boolean updatePadiglioneEvento(int id_evento, int id_padiglione, int id_utente, int tipo) throws SQLException;
    boolean readPadiglioneEvento(int id_evento, int id_padiglione, int id_utente) throws SQLException;
    ArrayList<PadiglioneEvento> readAllPadiglioniEvento() throws SQLException;
    ArrayList<PadiglioneEvento> readAllPadiglioniEvento(int id_utente) throws SQLException;
}
