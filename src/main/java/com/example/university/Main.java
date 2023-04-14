package com.example.university;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.logging.Level;

public class Main extends Application {

    {java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);}
    public static Session session;

    public static Transaction transaction = null;

    public Main() {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        Label lbl = new Label("Testing Hibernate");
        pane.getChildren().add(lbl);
        System.out.println("success!");
        Scene scene = new Scene(pane, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Main main = new Main();
//        launch();
    }
}