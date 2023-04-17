package com.example.university;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class HomeUtil {

    public static void changeScene(String fxmlFile, String title, int width, int height) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Main.stage.setTitle(title);
        try {
            Main.stage.setScene(new Scene(fxmlLoader.load(), width, height));
            Main.stage.centerOnScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void login(String email, String pass) {

        String hql = "FROM User WHERE email = :email";
        Query query = Main.session.createQuery(hql);
        query.setParameter("email", email);
        List<User> results = (List<User>) query.getResultList();

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

        Main.id = current.getId();
        if (current.getRole().equals("s"))
            changeScene("student.fxml", "Welcome Student", 1000, 750);
        else if (current.getRole().equals("a"))
            changeScene("admin.fxml", "Welcome Admin", 1000, 750);
        else if (current.getRole().equals("i"))
            changeScene("instructor.fxml", "Welcome Instructor", 1000, 750);
    }

    public static void signup(String name, String email, String pass, String confPass) {
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

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(pass, BCrypt.gensalt()));
        user.setRole("s");

        try {
            Main.session.save(user);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
                Main.transaction.commit();

            hql = "From User WHERE email = :email";
            query = Main.session.createQuery(hql);
            query.setParameter("email", email);
            int id = ((User) query.getResultList().get(0)).getId();

            Main.id = id;
            changeScene("student.fxml", "Welcome Student", 850, 640);
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}