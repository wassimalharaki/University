package com.example.university;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class HomeUtil {

    public static void changeScene(String fxmlFile, String title, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        try {
            Main.stage.setTitle(title);
            Main.stage.setScene(new Scene(fxmlLoader.load(), width, height));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void login(String email, String pass) {

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

        if (current.getRole().equals("s"))
            changeScene("student.fxml", "Welcome Student", 1000, 800);
        else if (current.getRole().equals("a"))
            changeScene("admin.fxml", "Welcome Admin", 1000, 800);
        else if (current.getRole().equals("i"))
            changeScene("instructor.fxml", "Welcome Instructor", 1000, 800);
    }

    public static void signUp(String name, String email, String pass, String confPass) {
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
            Main.transaction.commit();
            changeScene("student.fxml", "Welcome Student", 1000, 800);
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}