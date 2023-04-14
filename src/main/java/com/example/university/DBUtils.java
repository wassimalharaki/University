package com.example.university;

import javafx.event.ActionEvent;
import javafx.event.Event;
import org.hibernate.*;

public class DBUtils {

    private Session session;
    Transaction transaction = null;

    public DBUtils(){
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public static void changeScene(Event event, String fmxlFile, String title, int width, int height) {

    }
    public static void login(ActionEvent event, String email, String pass){

    }

    public static void signUp(ActionEvent event, String username, String email, String pass, String confPass){}

}
