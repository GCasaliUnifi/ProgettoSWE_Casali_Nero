package dao;

import model.Utente;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtenteDAO extends BaseDAO {
    private Utente utente;
    private static String salt = "A1B2C3D4E5F6G7H8I9J0";

    public UtenteDAO(Utente u) {
        utente = u;
    }

    private String getSecurePassword() {
        String generatedPsw = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(utente.getPassword().getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; ++i) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPsw = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return generatedPsw;
    }

    public boolean tryLogin() {
        if(utente != null) {
            try {
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND psw = ?");
                preparedStatement.setString(1, utente.geteMail());
                preparedStatement.setString(2, getSecurePassword());
//                preparedStatement.setString(2, utente.getPassword());
                if(preparedStatement.executeQuery().next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
