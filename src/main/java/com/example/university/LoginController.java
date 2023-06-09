package com.example.university;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
            if (tf_email.getText().trim().isEmpty() || pf_password.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to sign up!");
                alert.show();
            }
            else
                HomeUtil.login(tf_email.getText(), pf_password.getText());
        });

        btn_register.setOnAction(event -> {
            HomeUtil.changeScene("signup.fxml", "Sign Up", 850, 640);
        });
    }
}