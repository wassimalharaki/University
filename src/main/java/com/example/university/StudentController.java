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

public class StudentController implements Initializable {

    @FXML
    Button btn_getRegisteredCourses;

    @FXML
    Button btn_getAllCourses;

    @FXML
    TableView table_results;

    @FXML
    Label lbl_noResults;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_getRegisteredCourses.setOnAction(event -> {
            lbl_noResults.setVisible(false);
            table_results.getColumns().clear();
            table_results.getItems().clear();
            Student student = new Student();
            student.setId(Main.id);
            List<Course> registeredCourses = student.getRegisteredCourses();
            if (registeredCourses.size() == 0) {
                lbl_noResults.setVisible(true);
                return;
            }
            TableColumn<Map, String> c1 = new TableColumn<>("Course Name");
            c1.setCellValueFactory(new MapValueFactory<>("courseName"));
            c1.setPrefWidth(180);
            TableColumn<Map, String> c2 = new TableColumn<>("Instructor Name");
            c2.setCellValueFactory(new MapValueFactory<>("instructorName"));
            c2.setPrefWidth(180);
            TableColumn<Map, String> c3 = new TableColumn<>("Available");
            c3.setCellValueFactory(new MapValueFactory<>("available"));
            c3.setPrefWidth(180);
            TableColumn<Map, Button> c4 = new TableColumn<>("Action");
            c4.setCellValueFactory(new MapValueFactory<>("button"));
            c1.setPrefWidth(200);
//            c1.setStyle("-fx-background-color:  #3f51b5;");
//            c2.setStyle("-fx-background-color:  #3f51b5;");
//            c3.setStyle("-fx-background-color:  #3f51b5;");
//            c4.setStyle("-fx-background-color:  #3f51b5;");

            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c3);
            table_results.getColumns().add(c4);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();

            for (Course course : registeredCourses) {
                Button btn_dropCourse = new Button("DROP COURSE");
                btn_dropCourse.setOnAction(e -> {
                    student.dropCourse(course.getId());
                    btn_getRegisteredCourses.fire();
                });
                Map<String, Object> item = new HashMap<>();
                item.put("courseName", course.getName());
                item.put("instructorName", course.getInstructor().getName());
                item.put("available", " " + course.getAvailable());
                item.put("button", btn_dropCourse);
                btn_dropCourse.setStyle("-fx-background-color: #3f51b5; -fx-text-fill: #ffffff;");
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
            if (availableCourses.size() == 0) {
                lbl_noResults.setVisible(true);
                return;
            }
            TableColumn<Map, String> c1 = new TableColumn<>("Course Name");
            c1.setCellValueFactory(new MapValueFactory<>("courseName"));
            c1.setPrefWidth(200);
            TableColumn<Map, String> c2 = new TableColumn<>("Instructor Name");
            c2.setCellValueFactory(new MapValueFactory<>("instructorName"));
            c2.setPrefWidth(180);
            TableColumn<Map, Button> c4 = new TableColumn<>("Action");
            c4.setCellValueFactory(new MapValueFactory<>("button"));
            c1.setPrefWidth(200);
            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c4);

            ObservableList<Map<String, Object>> itemss =
                    FXCollections.observableArrayList();

            for (Course course : availableCourses) {
                Button btn_registerCourse = new Button("REGISTER COURSE");
                btn_registerCourse.setOnAction(e -> {
                    student.registerCourse(course.getId());
                    btn_getAllCourses.fire();
                });
                Map<String, Object> item = new HashMap<>();
                item.put("courseName", course.getName());
                item.put("instructorName", course.getInstructor().getName());
                item.put("button", btn_registerCourse);
                btn_registerCourse.setStyle("-fx-background-color: #3f51b5; -fx-text-fill: #ffffff;");
                itemss.add(item);
            }
            table_results.getItems().addAll(itemss);

        });
    }
}