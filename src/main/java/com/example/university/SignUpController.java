package com.example.university;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button btn_signup, btn_login;
    @FXML
    private TextField tf_username, tf_email;
    @FXML
    private PasswordField pf_password, pf_confPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_signup.setOnAction(event -> {
            if(!tf_username.getText().trim().isEmpty() &&
                    !tf_email.getText().trim().isEmpty() &&
                    !pf_password.getText().trim().isEmpty() &&
                    !pf_confPassword.getText().isEmpty())
            {
                String email = tf_email.getText().toString() + "@balamand.edu.lb";
                DBUtils.signUp(event, tf_username.getText(), email, pf_password.getText(), pf_confPassword.getText());
            } else{
                System.out.println("Please fill in all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to sign up!");
                alert.show();
            }
        });

        btn_login.setOnAction(event -> {
            DBUtils.changeScene(event, "Login.fxml","Login",700,400);
        });

    }
}

