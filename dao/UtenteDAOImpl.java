package dao;

import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDAOImpl extends DataBaseConnector implements UtenteDAO {
    private Utente utente;

    public UtenteDAOImpl(Utente u) {
        utente = u;
    }

    @Override
    public boolean createUtente(Utente utente) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(firstName, lastName, email, cellphone, psw, type) values (?, ?, ?, ?, ?, 0)");
            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getEmail());
            preparedStatement.setString(4, utente.getTelefono());
            preparedStatement.setString(5, utente.getPassword());

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
    public Utente readUtente(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                Utente utente = new Utente(rs.getString("email"), rs.getString("psw"));
                utente.setNome(rs.getString("firstName"));
                utente.setCognome(rs.getString("lastName"));
                utente.setId(rs.getInt("id"));
                utente.setTelefono(rs.getString("cellphone"));
                utente.setType(rs.getInt("type"));

                return utente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Utente readUtente(String email) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user where email = ?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                Utente utente = new Utente(rs.getString("email"), rs.getString("psw"));
                utente.setNome(rs.getString("firstName"));
                utente.setCognome(rs.getString("lastName"));
                utente.setId(rs.getInt("id"));
                utente.setTelefono(rs.getString("cellphone"));
                utente.setType(rs.getInt("type"));

                return utente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean updateUtente(Utente utente) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET firstName = ?, lastName = ?, cellphone = ?," +
                    " email = ?, psw = ?, type = ? WHERE id = ?");
            preparedStatement.setString(1, utente.getNome());
            preparedStatement.setString(2, utente.getCognome());
            preparedStatement.setString(3, utente.getTelefono());
            preparedStatement.setString(4, utente.getEmail());
            preparedStatement.setString(5, utente.getPassword());
            preparedStatement.setInt(6, utente.getType());
            preparedStatement.setInt(7, utente.getId());
            if(preparedStatement.executeUpdate() > 0) {
                System.out.println("Update eseguito con successo!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteUtente(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE from user WHERE id = ?");
            preparedStatement.setInt(1, id);
            if(preparedStatement.executeUpdate() > 0) {
                System.out.println("Cancellato con successo");
                return true;
            }
            // TODO finire di implementare cancellazioni a catena

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ArrayList<Utente> readAllCittadini() {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from user where type = ?");
            preparedStatement.setInt(1, 0);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Utente> cittadini = new ArrayList<>();
            while(rs.next()) {
                Utente u = new Utente(rs.getString("email"), rs.getString("psw"));
                u.setNome(rs.getString("firstName"));
                u.setCognome(rs.getString("lastName"));
                u.setId(rs.getInt("id"));
                u.setTelefono(rs.getString("cellphone"));
                u.setType(rs.getInt("type"));
                cittadini.add(u);
            }
            return cittadini;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}