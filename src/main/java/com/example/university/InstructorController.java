package com.example.university;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InstructorController implements Initializable {

    @FXML
    Button btn_viewCourses;

    @FXML
    Pane vbox_results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_viewCourses.setOnAction(event -> {
            vbox_results.getChildren().clear();
            Instructor instructor = new Instructor();
            instructor.setId(Main.id);
            List<Course> courses = instructor.getInstructedCourses();
            if (courses.size() == 0) {
                vbox_results.getChildren().add(new Label("NO COURSES"));
                return;
            }
            for (Course course: courses) {
                HBox hbox_course = new HBox();
                hbox_course.getChildren().add(new Label(course.toString()));
                Button btn_viewStudents = new Button("VIEW STUDENTS");
                btn_viewStudents.setOnAction(e -> {
                    vbox_results.getChildren().clear();
                    List<User> students = instructor.getStudentsRegisteredInCourse(course.getId());
                    if (students == null)
                        vbox_results.getChildren().add(new Label("INVALID COURSE"));
                    else if (students.size() == 0)
                        vbox_results.getChildren().add(new Label("NO STUDENTS"));
                    else
                        for (User student: students)
                            vbox_results.getChildren().add(new Label(student.toString()));
                });
                hbox_course.getChildren().add(btn_viewStudents);
                vbox_results.getChildren().add(hbox_course);
            }
        });
    }
}