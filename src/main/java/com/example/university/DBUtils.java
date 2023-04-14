package com.example.university;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import org.hibernate.query.Query;

import java.util.List;

public class DBUtils {

    public static void changeScene(Event event, String fmxlFile, String title, int width, int height) {

    }
    public static void login(ActionEvent event, String email, String pass) {
        String hql = "FROM User WHERE email = :eml and password= :pwd";

        Query query = Main.session.createQuery(hql);
        query.setParameter("eml", email);
        query.setParameter("pwd", pass);
        List<User> results = query.getResultList();

        if (results.size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Wrong email or password");
            alert.show();
            return;
        }

        User current = results.get(0);

        if (current.getRole().equals("s")) {
            changeScene(event, "LoggedInStudent.fxml", "Welcome Student", 1000, 800);
        }
        else if (current.getRole().equals("a")) {
            changeScene(event, "LoggedInAdmin.fxml", "Welcome Admin", 1000, 800);
        }
        else {
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
        user.setPassword(pass);
        user.setRole("s");
        try {
            Main.session.save(user);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            if (Main.transaction != null) {
                if (!Main.transaction.getStatus().equals("COMMITTED"))
                    Main.transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }
}