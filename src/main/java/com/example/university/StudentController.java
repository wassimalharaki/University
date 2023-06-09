package com.example.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    Button btn_getRegisteredCourses, btn_logout;

    @FXML
    Button btn_getAllCourses;

    @FXML
    TableView table_results;

    @FXML
    Label lbl_noResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        table_results.setPlaceholder(new Label("No Results"));

        btn_logout.setOnAction(event -> {
            Main.id = -1;
            HomeUtil.changeScene("login.fxml", "Sign In", 850, 640);
        });

        btn_getRegisteredCourses.setOnAction(event -> {

            table_results.getColumns().clear();
            table_results.getItems().clear();

            Student student = new Student();
            student.setId(Main.id);

            List<Course> registeredCourses = student.getRegisteredCourses();
            TableColumn<Map, String> c1 = new TableColumn<>("Course Name");
            c1.setCellValueFactory(new MapValueFactory<>("courseName"));
            TableColumn<Map, String> c2 = new TableColumn<>("Instructor Name");
            c2.setCellValueFactory(new MapValueFactory<>("instructorName"));
            TableColumn<Map, Button> c3 = new TableColumn<>("Action");
            c3.setCellValueFactory(new MapValueFactory<>("button"));

            table_results.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c3);
            table_results.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();

            for (Course course : registeredCourses) {
                Button btn_dropCourse = new Button("DROP COURSE");
                btn_dropCourse.setCursor(Cursor.HAND);
                btn_dropCourse.setOnAction(e -> {
                    student.dropCourse(course.getId());
                    btn_getRegisteredCourses.fire();
                });

                Map<String, Object> item = new HashMap<>();

                item.put("courseName", course.getName());
                item.put("instructorName", course.getInstructor().getName());
                item.put("button", btn_dropCourse);

                items.add(item);
            }

            table_results.getItems().addAll(items);
        });

        btn_getAllCourses.setOnAction(event -> {

            table_results.getColumns().clear();
            table_results.getItems().clear();

            Student student = new Student();
            student.setId(Main.id);

            List<Course> availableCourses = student.getAvailableCourses();

            TableColumn<Map, String> c1 = new TableColumn<>("Course Name");
            c1.setCellValueFactory(new MapValueFactory<>("courseName"));
            TableColumn<Map, String> c2 = new TableColumn<>("Instructor Name");
            c2.setCellValueFactory(new MapValueFactory<>("instructorName"));
            TableColumn<Map, Button> c3 = new TableColumn<>("Action");
            c3.setCellValueFactory(new MapValueFactory<>("button"));

            table_results.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c3);
            table_results.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();

            for (Course course : availableCourses) {
                Button btn_registerCourse = new Button("REGISTER COURSE");
                btn_registerCourse.setCursor(Cursor.HAND);
                btn_registerCourse.setOnAction(e -> {
                    student.registerCourse(course.getId());
                    btn_getAllCourses.fire();
                });

                Map<String, Object> item = new HashMap<>();

                item.put("courseName", course.getName());
                item.put("instructorName", course.getInstructor().getName());
                item.put("button", btn_registerCourse);

                items.add(item);
            }

            table_results.getItems().addAll(items);
        });
    }
}