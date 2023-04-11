package com.example.university;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        System.out.println("Hello Wassim, I am Omar");
        stage.setTitle("Hello!");
        stage.setScene(scene);
        System.out.println("Shu ma ken!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}