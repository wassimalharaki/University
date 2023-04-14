package com.example.university;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btn_login, btn_register;
    @FXML
    private TextField tf_email;
    @FXML
    private PasswordField pf_password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
