package dao;

import model.Evento;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EventoDAO {
    boolean createEvento(Evento evento) throws SQLException;
    Evento readEvento(int id) throws SQLException;
    Evento readEvento(String codice) throws SQLException;
    ArrayList<Evento> readAllEventi() throws SQLException;
    ArrayList<Evento> readAllEventi(int id_padiglione) throws SQLException;
    boolean updateEvento(Evento evento) throws SQLException;
    boolean deleteEvento(int id) throws SQLException;
}
