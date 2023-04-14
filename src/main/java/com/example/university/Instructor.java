package com.example.university;

import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Instructor extends User {

    public List<Course> getInstructedCourses() {
        String hql = "From Course WHERE instructor = :instructor";
        Query query = Main.session.createQuery(hql);
        query.setParameter("instructor", this);
        return (List<Course>) query.getResultList();
    }
    public static List<User> getStudentsRegisteredInCourse(Course course) {
        List<User> studentsRegisteredInCourse = new ArrayList<>();
        String hql = "From Registration WHERE course = :course";
        Query query = Main.session.createQuery(hql);
        query.setParameter("course", course);
        for (Registration registration: (List<Registration>) query.getResultList()) {
            studentsRegisteredInCourse.add(registration.getUser());
        }
        return studentsRegisteredInCourse;
    }
}