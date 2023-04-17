package com.example.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class InstructorController implements Initializable {

    @FXML
    Button btn_viewCourses, btn_logout;

    @FXML
    TableView table_results;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_results.setPlaceholder(new Label("No Results"));

        btn_logout.setOnAction(event -> {
            Main.id = -1;
            HomeUtil.changeScene("login.fxml", "Sign In", 850, 640);
        });

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
            TableColumn<Map, Button> c3 = new TableColumn<>("Action");
            c3.setCellValueFactory(new MapValueFactory<>("button"));

            table_results.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c3);
            table_results.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();

            for (Course course: instructedCourses) {
                Button btn_viewStudents = new Button("VIEW STUDENTS");
                btn_viewStudents.setCursor(Cursor.HAND);
                btn_viewStudents.setOnAction(e -> {
                    viewStudents(instructor, course);
                });

                Map<String, Object> item = new HashMap<>();

                item.put("courseName", course.getName());
                item.put("available", "" + course.getAvailable());
                item.put("button", btn_viewStudents);

                items.add(item);
            }

            table_results.setItems(items);
        });
    }

    private void viewStudents(Instructor instructor, Course course) {

        table_results.getColumns().clear();
        table_results.getItems().clear();

        List<User> students = instructor.getStudentsRegisteredInCourse(course);

        TableColumn<Map, String> c1 = new TableColumn<>("Name");
        c1.setCellValueFactory(new MapValueFactory<>("name"));
        TableColumn<Map, String> c2 = new TableColumn<>("Email");
        c2.setCellValueFactory(new MapValueFactory<>("email"));

        table_results.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table_results.getColumns().add(c1);
        table_results.getColumns().add(c2);
        table_results.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ObservableList<Map<String, Object>> items =
                FXCollections.observableArrayList();

        for (User user: students) {
            Map<String, Object> item = new HashMap<>();

            item.put("name", user.getName());
            item.put("email", user.getEmail());

            items.add(item);
        }

        table_results.getItems().addAll(items);
    }
}