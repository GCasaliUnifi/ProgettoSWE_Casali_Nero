package dao;

import model.Padiglione;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PadiglioneDAOImpl extends DataBaseConnector implements PadiglioneDAO {

    @Override
    public boolean createPadiglione(Padiglione padiglione) throws SQLException {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO padiglione(codice, dimensione) values (?, ?)");
            preparedStatement.setString(1, padiglione.getCodice());
            preparedStatement.setFloat(2, padiglione.getDimensione());

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
    public Padiglione readPadiglione(int id) throws SQLException {
        return null;
    }

    @Override
    public Padiglione readPadiglione(String codice) throws SQLException {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from padiglione where codice = ?");
            preparedStatement.setString(1, codice);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Padiglione(rs.getString("codice"), rs.getFloat("dimensione"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updatePadiglione(Padiglione padiglione) throws SQLException {
        return false;
    }

    @Override
    public boolean deletePadiglione(int id) throws SQLException {
        return false;
    }
}
