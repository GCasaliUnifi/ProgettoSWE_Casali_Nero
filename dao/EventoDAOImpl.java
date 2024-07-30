package dao;

import model.Evento;
import model.Padiglione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

public class EventoDAOImpl extends DataBaseConnector implements EventoDAO{


    @Override
    public boolean createEvento(Evento evento) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO evento(codice, nome, data, descrizione) values (?, ?, ?, ?)");
            preparedStatement.setString(1, evento.getCodice());
            preparedStatement.setString(2, evento.getNome());
            preparedStatement.setDate(3, Date.valueOf(evento.getData()));
            preparedStatement.setString(4, evento.getDescrizione());

            if(preparedStatement.executeUpdate() > 0) {
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
    public Evento readEvento(int id) throws SQLException {
        return null;
    }

    @Override
    public Evento readEvento(String codice) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from evento where codice = ?");
            preparedStatement.setString(1, codice);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                Evento ev = new Evento(rs.getString("codice"), rs.getString("nome"), rs.getString("data"), rs.getString("descrizione"));
                ev.setId(rs.getInt("id"));
                return ev;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Evento> readAllEventi() throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from evento");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Evento> eventi = new ArrayList<>();
            while(rs.next()) {
                Evento ev = new Evento(rs.getString("codice"), rs.getString("nome"), rs.getString("data"), rs.getString("descrizione"));
                ev.setId(rs.getInt("id"));
                eventi.add(ev);
            }

            return eventi;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateEvento(Evento evento) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteEvento(int id) throws SQLException {
        return false;
    }
}
