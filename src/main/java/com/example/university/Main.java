package com.example.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class Main extends Application {

    public static Stage stage;

    public static int id = -1;

    public static Session session;
    public static Transaction transaction = null;

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.getTransaction();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 640);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Sign In");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        Main main = new Main();
        launch();
    }
}