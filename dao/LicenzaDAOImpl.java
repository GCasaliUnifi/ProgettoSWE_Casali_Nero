package dao;

import model.Licenza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

public class LicenzaDAOImpl extends DataBaseConnector implements LicenzaDAO{

    @Override
    public boolean createLicenza(Licenza licenza) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO licenza(id_user, codice, scadenza) values (?, ?, ?)");
            preparedStatement.setInt(1, licenza.getId_utente());
            preparedStatement.setString(2, licenza.getCodice());
            preparedStatement.setDate(3, Date.valueOf(licenza.getScadenza()));

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
    public Licenza readLicenza(int id_utente) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from licenza where id_user = ?");
            preparedStatement.setInt(1, id_utente);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Licenza l = new Licenza(rs.getString("codice"));
                l.setId_utente(rs.getInt("id_user"));
                l.setScadenza(rs.getDate("scadenza").toString());
                l.setId(rs.getInt("id"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Licenza readLicenza(String codice) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from licenza where codice = ?");
            preparedStatement.setString(1, codice);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Licenza l = new Licenza(rs.getString("codice"));
                l.setId_utente(rs.getInt("id_user"));
                l.setScadenza(rs.getDate("scadenza").toString());
                l.setId(rs.getInt("id"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateLicenza(Licenza licenza) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE licenza SET id_user = ?, codice = ?, scadenza = ? WHERE id = ?");
            preparedStatement.setInt(1, licenza.getId_utente());
            preparedStatement.setString(2, licenza.getCodice());
            preparedStatement.setDate(3, Date.valueOf(licenza.getScadenza()));
            preparedStatement.setInt(4, licenza.getId());

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
    public boolean deleteLicenza(int id) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM licenza WHERE id = ?");
            preparedStatement.setInt(1, id);

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                throw new Exception("Errore durante l'eliminazione");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
