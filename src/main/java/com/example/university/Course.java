package com.example.university;

import jakarta.persistence.*;
import org.hibernate.query.Query;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int id;

    @Column(name = "course_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @Column(name = "is_available")
    private boolean available;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }
    public User getInstructor() {
        return instructor;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    public boolean getAvailable() {
        return available;
    }

    public static List<Course> getAvailableCourses() {
        String hql = "FROM Course WHERE available = :available";
        Query query = Main.session.createQuery(hql);
        query.setParameter("available", true);
        return (List<Course>) query.getResultList();
    }

    public static List<Course> getAllCourses() {
        String hql = "FROM Course";
        Query query = Main.session.createQuery(hql);
        return (List<Course>) query.getResultList();
    }

    public String toString() {
        return "ID: " + id
                + "\nName: " + name
                + "\nInstructor Name: " + instructor.getName()
                + "\nAvailable: " + available;
    }
}