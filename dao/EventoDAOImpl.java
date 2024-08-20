package dao;

import model.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

public class EventoDAOImpl extends DataBaseConnector implements EventoDAO {


    @Override
    public boolean createEvento(Evento evento) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO evento(codice, nome, data, descrizione) values (?, ?, ?, ?)");
            preparedStatement.setString(1, evento.getCodice());
            preparedStatement.setString(2, evento.getNome());
            preparedStatement.setDate(3, Date.valueOf(evento.getData()));
            preparedStatement.setString(4, evento.getDescrizione());

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
    public Evento readEvento(int id) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM evento WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Evento ev = new Evento(rs.getString("codice"), rs.getString("nome"), rs.getString("data"), rs.getString("descrizione"));
                ev.setId(rs.getInt("id"));
                ev.setPosti(rs.getInt("posti"));
                return ev;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Evento readEvento(String codice) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from evento where codice = ?");
            preparedStatement.setString(1, codice);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Evento ev = new Evento(rs.getString("codice"), rs.getString("nome"), rs.getString("data"), rs.getString("descrizione"));
                ev.setId(rs.getInt("id"));
                ev.setPosti(rs.getInt("posti"));
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
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from evento ORDER BY data");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Evento> eventi = new ArrayList<>();
            while (rs.next()) {
                //Conversione della data in formato gg/mm/aaaa per la visualizzazione
                String data = rs.getDate("data").toString();
                String[] dataSplit = data.split("-");
                data = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
                Evento ev = new Evento(rs.getString("codice"), rs.getString("nome"), data, rs.getString("descrizione"));
                ev.setId(rs.getInt("id"));
                ev.setPosti(rs.getInt("posti"));
                eventi.add(ev);
            }

            return eventi;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Evento> readAllEventi(int id_padiglione) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT evento.id as id, evento.data as data, evento.nome as nome " +
                    "from evento " +
                    "where evento.id NOT IN (SELECT padiglione_evento.id_evento from padiglione_evento where padiglione_evento.id_padiglione = ?)");
            preparedStatement.setInt(1, id_padiglione);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Evento> eventi = new ArrayList<>();
            while (rs.next()) {
                //Conversione della data in formato gg/mm/aaaa per la visualizzazione
                String data = rs.getDate("data").toString();
                String[] dataSplit = data.split("-");
                data = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
                Evento ev = new Evento("", rs.getString("nome"), data, "");
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
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE evento SET codice = ?, nome = ?, data = ?, descrizione = ?, posti = ? WHERE id = ?");
            preparedStatement.setString(1, evento.getCodice());
            preparedStatement.setString(2, evento.getNome());
            preparedStatement.setDate(3, Date.valueOf(evento.getData()));
            preparedStatement.setString(4, evento.getDescrizione());
            preparedStatement.setInt(5, evento.getPosti());
            preparedStatement.setInt(6, evento.getId());

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                throw new Exception("Errore durante l'aggiornamento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteEvento(int id) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM evento WHERE id = ?");
            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                throw new Exception("Errore durante l'aggiornamento");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
