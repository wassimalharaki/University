package com.example.university;

import java.util.ArrayList;

public class Instructor extends User {

    ArrayList<Course> coursesInstructed = new ArrayList<>();

    public Instructor() {
        setRole("i");
    }

    public ArrayList<Course> getCoursesInstructed() {
        return coursesInstructed;
    }

    public ArrayList<Student> getStudentsRegisteredInCourse(Course course) {
        ArrayList<Student> studentsRegisteredInCourse = new ArrayList<>();
        //TO DO
        return studentsRegisteredInCourse;
    }
}