package com.example.university;

import java.util.ArrayList;

public class Student extends User {

    public Student() {
        setRole("s");
    }
    private ArrayList<Course> CoursesRegistered = new ArrayList<>();

    public ArrayList<Course> getRegisteredCourses() {
        return CoursesRegistered;
    }

    public void registerCourse(Course course) {
        try {
            Registration registration = new Registration();
            registration.setUser(this);
            registration.setCourse(course);
            CoursesRegistered.add(course);
        } catch (Exception e) {
            System.out.println("Error registering for course: " + e.getMessage());
        } finally {
            System.out.println("Registration process completed.");
        }
    }

    public void dropCourse(Course course) {
        try {
            for (Course courses : CoursesRegistered) {
                if (courses.equals(course)) {
                    CoursesRegistered.remove(course);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error dropping course: " + e.getMessage());
        } finally {
            System.out.println("Dropping process completed.");
        }
    }
}
