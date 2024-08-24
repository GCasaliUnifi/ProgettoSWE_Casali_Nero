package dao;

import model.Padiglione;
import model.PadiglioneEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class PadiglioneEventoDAOImpl extends DataBaseConnector implements PadiglioneEventoDAO{

    @Override
    public boolean createPadiglioneEvento(int id_evento, int id_padiglione, int id_utente, int tipo) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO padiglione_evento(id_evento, id_padiglione, id_utente, tipo) values (?, ?, ?, ?)");
            preparedStatement.setInt(1, id_evento);
            preparedStatement.setInt(2, id_padiglione);
            preparedStatement.setInt(3, id_utente);
            preparedStatement.setInt(4, tipo);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                throw new Exception("Errore durante l'inserimento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deletePadiglioneEvento(int id_evento, int id_padiglione, int id_utente) throws SQLException {
        return false;
    }

    @Override
    public boolean updatePadiglioneEvento(int id_evento, int id_padiglione, int id_utente, int tipo) throws SQLException {
        return false;
    }

    @Override
    public boolean readPadiglioneEvento(int id_evento, int id_padiglione, int id_utente) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<PadiglioneEvento> readAllPadiglioniEvento() throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from padiglione_evento");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<PadiglioneEvento> padiglioni = new ArrayList<>();
            while (rs.next()) {
                PadiglioneEvento p = new PadiglioneEvento(rs.getInt("id_evento"), rs.getInt("id_padiglione"), rs.getInt("id_utente"), rs.getInt("tipo"));
                //recupera il padiglione con l'id corrispondente
                PadiglioneDAOImpl padiglioneDAO = new PadiglioneDAOImpl();
                p.setCodicePadiglione(padiglioneDAO.readPadiglione(rs.getInt("id_padiglione")).getCodice());
                //recupera l'evento con l'id corrispondente
                EventoDAOImpl eventoDAO = new EventoDAOImpl();
                p.setNomeEvento(eventoDAO.readEvento(rs.getInt("id_evento")).getNome());
                padiglioni.add(p);
            }

            return padiglioni;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<PadiglioneEvento> readAllPadiglioniEvento(int id_utente) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM padiglione_evento WHERE id_utente = ?");
            preparedStatement.setInt(1, id_utente);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<PadiglioneEvento> padiglioni = new ArrayList<>();
            while (rs.next()) {
                PadiglioneEvento p = new PadiglioneEvento(rs.getInt("id_evento"), rs.getInt("id_padiglione"), rs.getInt("id_utente"), rs.getInt("tipo"));
                //recupera il padiglione con l'id corrispondente
                PadiglioneDAOImpl padiglioneDAO = new PadiglioneDAOImpl();
                p.setCodicePadiglione(padiglioneDAO.readPadiglione(rs.getInt("id_padiglione")).getCodice());
                //recupera l'evento con l'id corrispondente
                EventoDAOImpl eventoDAO = new EventoDAOImpl();
                p.setNomeEvento(eventoDAO.readEvento(rs.getInt("id_evento")).getNome());
                padiglioni.add(p);
            }

            return padiglioni;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
