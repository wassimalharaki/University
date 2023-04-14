package com.example.university;

import jakarta.persistence.*;

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

    public String toString() {
        return "ID: " + id
                + "\nName: " + name
                + "\nInstructor Name: " + instructor.getName()
                + "\nAvailable: " + available;
    }
}