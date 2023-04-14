package com.example.university;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private Button btn_login, btn_register;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField pf_password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_login.setOnAction(event -> {
            if (!tf_email.getText().trim().isEmpty() && !pf_password.getText().trim().isEmpty())
                DBUtils.login(tf_email.getText(), pf_password.getText());
        });

        btn_register.setOnAction(event -> {
            DBUtils.changeScene("signup.fxml", "Sign Up", 1000, 800);
        });
    }
}
