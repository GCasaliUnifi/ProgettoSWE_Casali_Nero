package dao;

import model.Notifica;

import java.sql.*;
import java.util.ArrayList;


public class NotificaDAOImpl extends DataBaseConnector implements NotificaDAO {
    private Notifica notifica;

    public NotificaDAOImpl(Notifica n) {
        notifica = n;
    }

    @Override
    public boolean createNotifica(Notifica notifica) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO notifica(tipo, id_utente, messaggio) values (?, ?, ?)");
            preparedStatement.setInt(1, notifica.getTipo());
            preparedStatement.setInt(2, notifica.getId_utente());
            preparedStatement.setString(3, notifica.getMessaggio());

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
    public ArrayList<Notifica> readNotifiche(int id_utente) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM notifica WHERE id_utente = ? ORDER BY data DESC");
            preparedStatement.setInt(1, id_utente);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Notifica> notifiche = new ArrayList<>();
            while (rs.next()) {
                Notifica notifica = new Notifica(rs.getInt("tipo"), rs.getInt("id_utente"), rs.getString("messaggio"));
                notifica.setId(rs.getInt("id"));
                notifica.setData(rs.getString("data"));
                notifica.setStato(rs.getInt("stato"));
                notifiche.add(notifica);
            }
            return notifiche;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateNotifica(Notifica notifica) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE notifica SET tipo = ?, id_utente = ?, messaggio = ?, data = ?, stato = ? WHERE id = ?");
            preparedStatement.setInt(1, notifica.getTipo());
            preparedStatement.setInt(2, notifica.getId_utente());
            preparedStatement.setString(3, notifica.getMessaggio());
            preparedStatement.setString(4, notifica.getData());
            preparedStatement.setInt(5, notifica.getStato());
            preparedStatement.setInt(6, notifica.getId());

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
    public boolean deleteNotifica(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM notifica WHERE id = ?");
            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Notifica eliminata");
                return true;
            } else {
                throw new Exception("Errore durante l'eliminazione");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Notifica> readAllNotifiche() {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from notifica");
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Notifica> notifiche = new ArrayList<>();
            while (rs.next()) {
                Notifica notifica = new Notifica(rs.getInt("tipo"), rs.getInt("id_utente"), rs.getString("messaggio"));
                notifica.setId(rs.getInt("id"));
                notifica.setData(rs.getString("data"));
                notifica.setStato(rs.getInt("stato"));
                notifiche.add(notifica);
            }
            return notifiche;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
