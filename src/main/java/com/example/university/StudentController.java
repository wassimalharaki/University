package com.example.university;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    Button btn_getRegisteredCourses;

    @FXML
    Button btn_getAllCourses;

    @FXML
    VBox vbox_results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_getRegisteredCourses.setOnAction(event -> {
            vbox_results.getChildren().clear();
            Student student = new Student();
            student.setId(Main.id);
            List<Course> registeredCourses = student.getRegisteredCourses();
            if (registeredCourses.size() == 0) {
                vbox_results.getChildren().add(new Label("NO COURSES"));
                return;
            }
            for (Course course: registeredCourses) {
                HBox hbox_course = new HBox();
                hbox_course.getChildren().add(new Label(course.toString()));
                Button btn_dropCourse = new Button("DROP COURSE");
                btn_dropCourse.setOnAction(e -> {
                    student.dropCourse(course.getId());
                    btn_getRegisteredCourses.fire();
                });
                hbox_course.getChildren().add(btn_dropCourse);
                vbox_results.getChildren().add(hbox_course);
            }
        });
        btn_getAllCourses.setOnAction(event -> {
            vbox_results.getChildren().clear();
            Student student = new Student();
            student.setId(Main.id);
            List<Course> availableCourses = student.getAvailableCourses();
            if (availableCourses.size() == 0) {
                vbox_results.getChildren().add(new Label("NO COURSES"));
                return;
            }
            for (Course course: availableCourses) {
                HBox hbox_course = new HBox();
                hbox_course.getChildren().add(new Label(course.toString()));
                Button btn_registerCourse = new Button("REGISTER COURSE");
                btn_registerCourse.setOnAction(e -> {
                    student.registerCourse(course.getId());
                    btn_getAllCourses.fire();
                });
                hbox_course.getChildren().add(btn_registerCourse);
                vbox_results.getChildren().add(hbox_course);
            }
        });
    }
}