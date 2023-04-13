module com.example.university {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.hibernate.orm.core;

    opens com.example.university to javafx.fxml;
    exports com.example.university;
}