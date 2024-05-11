package src;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp {
    private String firstName, lastName, email, cellphone, password, confirmPassword;

    public SignUp(String firstName, String lastName, String email, String cellphone, String password, String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void trySignUp() {
        //controlla che i campi non siano vuoti
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || cellphone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("Errore: uno o più campi sono vuoti.");
            return;
        }
        //controlla che le password siano uguali
        if (!password.equals(confirmPassword)) {
            System.out.println("Errore: le password non coincidono.");
            return;
        }
        //controlla che l'email sia valida
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Errore: l'email non è valida.");
            return;
        }
        //controlla che il numero di cellulare sia valido
        if (cellphone.length() != 10) {
            System.out.println("Errore: il numero di cellulare non è valido.");
            return;
        }
        //cifratura della password
        SecurePassword securePassword = new SecurePassword(password);
        password = securePassword.getSecurePassword();
        //controlla che l'email o il numero di cellulare non siano già presenti nel database
        try{
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM user WHERE email = ? OR cellphone = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, cellphone);
            if (preparedStatement.executeQuery().next()) {
                System.out.println("Errore: l'email o il numero di cellulare sono già presenti nel database.");
            }else{
                preparedStatement = DatabaseConnection.getConnection().prepareStatement("INSERT INTO user (firstName, lastName, email, cellphone, psw, type) VALUES (?, ?, ?, ?, ?, 0)");
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, cellphone);
                preparedStatement.setString(5, password);
                preparedStatement.executeUpdate();
                System.out.println("Registrazione avvenuta con successo.");
            }
        }catch (SQLException e){
            System.out.println("Errore: " + e.getMessage());
        }
        finally {
            DatabaseConnection.closeConnection();
        }
    }
}
