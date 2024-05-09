package gui;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import src.SignIn;
import src.SignUp;

public class SignUpController implements Controller{
    private MyGestionale myGestionale;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField cellphone;
    @FXML
    private TextField email;
    @FXML
    private TextField psw;
    @FXML
    private TextField pswConfirm;
    @FXML
    private Button accedi;
    @FXML
    private Button conferma;

    private SignUp SignUp;

    @Override
    public void setMyGestionale(MyGestionale myGestionale) {
        this.myGestionale = myGestionale;
    }

    @FXML
    public void initialize() {
        accedi.setOnAction(event -> {
            MyGestionale myGestionale = new MyGestionale();
            try {
                myGestionale.showSignInScreen();
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        });
        conferma.setOnAction(event -> {
            SignUp = new SignUp(firstName.getText(), lastName.getText(), email.getText(), cellphone.getText(), psw.getText(), pswConfirm.getText());
            SignUp.trySignUp();
        });
    }
}
