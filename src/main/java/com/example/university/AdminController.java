package com.example.university;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    Button btn_getUsers, btn_getCourses, btn_userConfirm, btn_courseConfirm, btn_addUser, btn_addCourse;

    @FXML
    ComboBox combo_users, combo_instructors, combo_available;

    @FXML
    TableView table_results;

    @FXML
    Pane pane_userAction, pane_courseAction;

    @FXML
    TextField tf_name, tf_email, tf_role, tf_courseName;

    @FXML
    PasswordField pf_password, pf_confirmPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_addUser.setOnAction(event -> {
            Admin admin = new Admin();
            admin.setId(Main.id);
            userAction(admin);
        });

        btn_addCourse.setOnAction(event -> {
            Admin admin = new Admin();
            admin.setId(Main.id);
            courseAction(admin);
        });

        btn_getUsers.setOnAction(event -> {

            pane_courseAction.setVisible(false);
            pane_userAction.setVisible(false);
            table_results.setVisible(true);

            table_results.getColumns().clear();
            table_results.getItems().clear();

            Admin admin = new Admin();
            admin.setId(Main.id);

            String role = combo_users.getValue().toString();
            List<User> users = null;

            if (role.equals("All"))
                users = admin.getUsers();
            else if (role.equals("Students"))
                users = admin.getStudents();
            else if (role.equals("Instructors"))
                users = admin.getInstructors();
            else if (role.equals("Admins"))
                users = admin.getAdmins();

            TableColumn<Map, String> c1 = new TableColumn<>("Name");
            c1.setCellValueFactory(new MapValueFactory<>("name"));
            TableColumn<Map, String> c2 = new TableColumn<>("Email");
            c2.setCellValueFactory(new MapValueFactory<>("email"));
            TableColumn<Map, String> c3 = new TableColumn<>("Role");
            c3.setCellValueFactory(new MapValueFactory<>("role"));
            TableColumn<Map, HBox> c4 = new TableColumn<>("Action");
            c4.setCellValueFactory(new MapValueFactory<>("container"));

            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c3);
            table_results.getColumns().add(c4);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();
            
            for (User user: users) {
                HBox box_action = new HBox();

                Button btn_update = new Button("UPDATE");
                btn_update.setOnAction(e -> {
                    userAction(admin, user);
                });

                Button btn_remove = new Button("REMOVE");
                btn_remove.setOnAction(e -> {
                    if (user.getId() == Main.id) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("You can't delete yourself!");
                        alert.show();
                        return;
                    }
                    admin.removeUser(user);
                    btn_getUsers.fire();
                });

                box_action.getChildren().addAll(btn_update, btn_remove);

                Map<String, Object> item = new HashMap<>();
                item.put("name", user.getName());
                item.put("email", user.getEmail());
                item.put("role", user.getRole());
                item.put("container", box_action);

                items.add(item);
            }

            table_results.getItems().addAll(items);
        });

        btn_getCourses.setOnAction(event -> {

            pane_userAction.setVisible(false);
            pane_courseAction.setVisible(false);
            table_results.setVisible(true);

            table_results.getColumns().clear();
            table_results.getItems().clear();

            Admin admin = new Admin();
            admin.setId(Main.id);

            List<Course> courses = Course.getAllCourses();

            TableColumn<Map, String> c1 = new TableColumn<>("Course Name");
            c1.setCellValueFactory(new MapValueFactory<>("courseName"));
            TableColumn<Map, String> c2 = new TableColumn<>("Instructor Name");
            c2.setCellValueFactory(new MapValueFactory<>("instructorName"));
            TableColumn<Map, String> c3 = new TableColumn<>("Available");
            c3.setCellValueFactory(new MapValueFactory<>("available"));
            TableColumn<Map, HBox> c4 = new TableColumn<>("Action");
            c4.setCellValueFactory(new MapValueFactory<>("container"));

            table_results.getColumns().add(c1);
            table_results.getColumns().add(c2);
            table_results.getColumns().add(c3);
            table_results.getColumns().add(c4);

            ObservableList<Map<String, Object>> items =
                    FXCollections.observableArrayList();

            for (Course course: courses) {
                HBox box_action = new HBox();

                Button btn_update = new Button("UPDATE");
                btn_update.setOnAction(e -> {
                    courseAction(admin, course);
                });

                Button btn_remove = new Button("REMOVE");
                btn_remove.setOnAction(e -> {
                    admin.removeCourse(course);
                    btn_getCourses.fire();
                });

                box_action.getChildren().addAll(btn_update, btn_remove);

                Map<String, Object> item = new HashMap<>();
                item.put("courseName", course.getName());
                item.put("instructorName", course.getInstructor().getName());
                item.put("available", course.getAvailable());
                item.put("container", box_action);

                items.add(item);
            }

            table_results.getItems().addAll(items);
        });
    }

    public void userAction(Admin admin, User user) {

        pane_courseAction.setVisible(false);
        table_results.setVisible(false);
        pane_userAction.setVisible(true);

        tf_name.setText(user.getName());
        tf_email.setText(user.getEmail());
        pf_confirmPassword.clear();
        pf_password.clear();
        tf_role.setText(user.getRole());

        btn_userConfirm.setOnAction(e -> {

            if (tf_name.getText().trim().isEmpty()
                    || tf_email.getText().trim().isEmpty()
                    || tf_role.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to update a user!");
                alert.show();
                return;
            }

            if (!pf_password.getText().equals(pf_confirmPassword.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Make sure you entered the password correctly in both fields.");
                alert.show();
                return;
            }

            admin.updateUser(user, tf_name.getText().trim(), tf_email.getText().trim(),
                    pf_password.getText().trim(), tf_role.getText().trim());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("SUCCESS");
            alert.show();
        });
    }

    public void userAction(Admin admin) {

        pane_courseAction.setVisible(false);
        table_results.setVisible(false);
        pane_userAction.setVisible(true);

        tf_name.clear();
        tf_email.clear();
        pf_confirmPassword.clear();
        pf_password.clear();
        tf_role.clear();

        btn_userConfirm.setOnAction(e -> {

            if (tf_name.getText().trim().isEmpty()
                    || tf_email.getText().trim().isEmpty()
                    || pf_password.getText().trim().isEmpty()
                    || pf_confirmPassword.getText().trim().isEmpty()
                    || tf_role.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to add a user!");
                alert.show();
                return;
            }

            if (!pf_password.getText().equals(pf_confirmPassword.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Make sure you entered the password correctly in both fields.");
                alert.show();
                return;
            }

            admin.addUser(tf_name.getText().trim(), tf_email.getText().trim(),
                    pf_password.getText().trim(), tf_role.getText().trim());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("SUCCESS");
            alert.show();
        });
    }


    public void courseAction(Admin admin, Course course) {

        pane_userAction.setVisible(false);
        table_results.setVisible(false);
        pane_courseAction.setVisible(true);

        tf_courseName.setText(course.getName());
        combo_available.setValue(course.getAvailable() + "");

        combo_instructors.getItems().clear();
        List<User> instructors = admin.getInstructors();
        for (User user: instructors)
            combo_instructors.getItems().add(user);
        combo_instructors.setValue(course.getInstructor());

        btn_courseConfirm.setOnAction(e -> {

            if (tf_courseName.getText().trim().isEmpty()
                    || combo_instructors.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to update a course!");
                alert.show();
                return;
            }

            admin.updateCourse(course, tf_courseName.getText().trim(), (User) combo_instructors.getValue(),
                    Boolean.parseBoolean((String) combo_available.getValue()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("SUCCESS");
            alert.show();
        });

    }

    public void courseAction(Admin admin) {

        pane_userAction.setVisible(false);
        table_results.setVisible(false);
        pane_courseAction.setVisible(true);

        tf_courseName.clear();
        combo_available.setValue("true");

        combo_instructors.getItems().clear();
        List<User> instructors = admin.getInstructors();
        for (User user: instructors)
            combo_instructors.getItems().add(user);

        btn_courseConfirm.setOnAction(e -> {

            if (tf_courseName.getText().trim().isEmpty()
                    || combo_instructors.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information to add a course!");
                alert.show();
                return;
            }

            admin.addCourse(tf_courseName.getText().trim(), (User) combo_instructors.getValue(),
                    Boolean.parseBoolean((String) combo_available.getValue()));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("SUCCESS");
            alert.show();
        });
    }
}