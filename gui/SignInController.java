package gui;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import src.SignIn;

public class SignInController implements Controller{
    private MyGestionale myGestionale;
    @FXML
    private TextField email;
    @FXML
    private TextField psw;
    @FXML
    private Button accedi;
    @FXML
    private Button registrati;

    private SignIn signIn;

    @Override
    public void setMyGestionale(MyGestionale myGestionale) {
        this.myGestionale = myGestionale;
    }

    @FXML
    public void initialize() {
        accedi.setOnAction(event -> {
            if(email.getText().isEmpty() || psw.getText().isEmpty()){
                System.out.println("Errore: uno o più campi sono vuoti.");
            }else{
                signIn = new SignIn(email.getText(), psw.getText());
                signIn.trySignIn();
            }
        });
        if(registrati != null){
            registrati.setOnAction(event -> {
                MyGestionale myGestionale = new MyGestionale();
                try {
                    myGestionale.showSignUpScreen();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }else{
            System.out.println("Errore: registrati è null.");
        }

    }
}
