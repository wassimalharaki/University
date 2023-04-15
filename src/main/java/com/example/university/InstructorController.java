package com.example.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class InstructorController implements Initializable {

    @FXML
    Button btn_viewCourses;

    @FXML
    TableView table_results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_viewCourses.setOnAction(event -> {
            table_results.getColumns().clear();
            table_results.getItems().clear();
            Instructor instructor = new Instructor();
            instructor.setId(Main.id);
            List<Course> instructedCourses = instructor.getInstructedCourses();

            TableColumn<Map, String> c1 = new TableColumn<>("Course Name");
            c1.setCellValueFactory(new MapValueFactory<>("courseName"));
            TableColumn<Map, String> c2 = new TableColumn<>("Available");
            c2.setCellValueFactory(new MapValueFactory<>("available"));

            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();

            for (Course course: instructedCourses) {
                Map<String, Object> item = new HashMap<>();
                item.put("courseName", course.getName());
                item.put("available", " " + course.getAvailable());
                items.add(item);
            }
            table_results.setItems(items);
//            table_results.getItems().addAll(items);

//            vbox_results.getChildren().clear();
//            Instructor instructor = new Instructor();
//            instructor.setId(Main.id);
//            List<Course> courses = instructor.getInstructedCourses();
//            if (courses.size() == 0) {
//                vbox_results.getChildren().add(new Label("NO COURSES"));
//                return;
//            }
//            for (Course course: courses) {
//                HBox hbox_course = new HBox();
//                hbox_course.getChildren().add(new Label(course.toString()));
//                Button btn_viewStudents = new Button("VIEW STUDENTS");
//                btn_viewStudents.setOnAction(e -> {
//                    vbox_results.getChildren().clear();
//                    List<User> students = instructor.getStudentsRegisteredInCourse(course.getId());
//                    if (students == null)
//                        vbox_results.getChildren().add(new Label("INVALID COURSE"));
//                    else if (students.size() == 0)
//                        vbox_results.getChildren().add(new Label("NO STUDENTS"));
//                    else
//                        for (User student: students)
//                            vbox_results.getChildren().add(new Label(student.toString()));
//                });
//                hbox_course.getChildren().add(btn_viewStudents);
//                vbox_results.getChildren().add(hbox_course);
//            }
        });
    }
}