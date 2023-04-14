package com.example.university;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

public class DBUtils {

    public static void changeScene(Event event, String fmxlFile, String title, int width, int height) {
        Parent root = null;

        try {
        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fmxlFile));
        root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Image icon = new Image(DBUtils.class.getResourceAsStream("images/logo.png"));
        stage.getIcons().add(icon);
        stage.setTitle(title);
        stage.setScene(new Scene(root, width, height));
        stage.setX((Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);
        stage.setResizable(false);
        stage.show();
    }

    public static void login(ActionEvent event, String email, String pass) {

        String hql = "FROM User WHERE email = :eml";
        Query query = Main.session.createQuery(hql);
        query.setParameter("eml", email);
        List<User> results = query.getResultList();

        if (results.size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User not Found");
            alert.show();
            return;
        }

        User current = results.get(0);
        String retrievedPass = current.getPassword();
        if (!BCrypt.checkpw(pass, retrievedPass)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The provided credentials are incorrect");
            alert.show();
            return;
        }

        if (current.getRole().equals("s")) {
            changeScene(event, "LoggedInStudent.fxml", "Welcome Student", 1000, 800);
        } else if (current.getRole().equals("a")) {
            changeScene(event, "LoggedInAdmin.fxml", "Welcome Admin", 1000, 800);
        } else if (current.getRole().equals("i")) {
            changeScene(event, "LoggedInInstructor.fxml", "Welcome Instructor", 1000, 800);
        }
    }

    public static void signUp(ActionEvent event, String name, String email, String pass, String confPass) {
        if (!pass.equals(confPass)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Make sure you entered the password correctly in both fields.");
            alert.show();
            return;
        }

        String hql = "FROM User WHERE email = :email";
        Query query = Main.session.createQuery(hql);
        query.setParameter("email", email);
        List results = query.list();

        if (results.size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User already exists!");
            alert.show();
            return;
        }

        User user = new Student();
        user.setName(name);
        user.setEmail(email);
        String hashedPassword = BCrypt.hashpw(pass, BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user.setRole("s");

        try {
            Main.session.save(user);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
            changeScene(event, "LoggedInStudent.fxml", "Welcome Student", 1000, 800);
        } catch (Exception e) {
            if (Main.transaction != null)
                if (!Main.transaction.getStatus().equals("COMMITTED"))
                    Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}