package com.example.university;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import org.hibernate.query.Query;

import java.util.List;

public class DBUtils {

    public static void changeScene(Event event, String fmxlFile, String title, int width, int height) {

    }
    public static void login(ActionEvent event, String email, String pass){
        try {
            String hql = "FROM User WHERE email = :eml and password= :pwd";

            Query query = Main.session.createQuery(hql);
            query.setParameter("eml", email);
            query.setParameter("pwd", pass);
            List results = query.list();

            if (results.size() > 0) {
                User current = (User) results.get(0);

                    if(current.getRole().equals("s")){
                        changeScene(event, "LoggedInStudent.fxml", "Welcome Student", 1000, 800);
                    }
                    else if(current.getRole().equals("a")){
                        changeScene(event, "LoggedInAdmin.fxml", "Welcome Admin", 1000, 800);
                    }
                    else{
                        changeScene(event, "LoggedInInstructor.fxml", "Welcome Admin", 1000, 800);
                    }

            } else {
                System.out.println("Wrong username/password");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wrong email or password");
                alert.show();
            }
        } catch (Exception e) {
            System.out.println("::::::::::::::::::::::::::");
            System.out.println(e.toString());
            System.out.println("::::::::::::::::::::::::::");

            if (Main.transaction != null) {
                if (!Main.transaction.getStatus().equals("COMMITTED") && Main.transaction.isActive())
                    Main.transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void signUp(ActionEvent event, String username, String email, String pass, String confPass){
        try {
            if(!pass.equals(confPass)) {
                String hql = "FROM User WHERE email = :email";
                Query query = Main.session.createQuery(hql);
                query.setParameter("email", email);
                List results = query.list();

                if(results.size() > 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("User already exists!");
                    alert.show();
                }
                else{
                    User newUser = new Student();
                    newUser.setName(username);
                    newUser.setEmail(email);
                    newUser.setPassword(pass);
                    newUser.setRole("s");

                }

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Make sure you entered the password correctly in both fields.\"");
                alert.show();
            }

        } catch (Exception e){
            System.out.println("::::::::::::::::::::::::::");
            System.out.println(e.toString());
            System.out.println("::::::::::::::::::::::::::");

            if (Main.transaction != null) {
                if (!Main.transaction.getStatus().equals("COMMITTED") && Main.transaction.isActive())
                    Main.transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
