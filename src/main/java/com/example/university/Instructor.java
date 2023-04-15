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

    public List<User> getStudentsRegisteredInCourse(int id) {
        Course course = new Course();
        course.setId(id);
        return getStudentsRegisteredInCourse(course);
    }

    public List<User> getStudentsRegisteredInCourse(String name) {
        String hql = "From Course WHERE name = :name";
        Query query = Main.session.createQuery(hql);
        query.setParameter("name", name);
        if (query.getResultList().size() != 1)
            return null;
        Course course = (Course) query.getResultList().get(0);
        return getStudentsRegisteredInCourse(course);
    }

    public List<User> getStudentsRegisteredInCourse(Course course) {
        if (!checkCourse(course))
            return null;
        List<User> studentsRegisteredInCourse = new ArrayList<>();
        String hql = "From Registration WHERE course = :course";
        Query query = Main.session.createQuery(hql);
        query.setParameter("course", course);
        for (Registration registration: (List<Registration>) query.getResultList()) {
            studentsRegisteredInCourse.add(registration.getUser());
        }
        return studentsRegisteredInCourse;
    }

    private boolean checkCourse(Course course) {
        List<Course> courses = getInstructedCourses();
        for (Course crs: courses) {
            if (course.getId() == crs.getId())
                return true;
        }
        return false;
    }
}