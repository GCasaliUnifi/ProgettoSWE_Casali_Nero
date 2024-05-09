package src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignIn{
    private String email, password;

    public SignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void trySignIn() {
        //controlla che i campi non siano vuoti
        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Errore: uno o più campi sono vuoti.");
            return;
        }
        //controlla che l'email sia valida
        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Errore: l'email non è valida.");
            return;
        }
        //controlla che l'email e la password siano presenti nel database
        try{
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM user WHERE email = ? AND psw = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            if (preparedStatement.executeQuery().next()) {
                System.out.println("Accesso effettuato con successo.");
            }else{
                System.out.println("Errore: l'email o la password non sono corrette.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection();
        }
    }

}
