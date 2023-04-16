package com.example.university;

import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public class Student extends  User{

    public List<Course> getRegisteredCourses(){
        String hql = "SELECT course FROM Registration WHERE user = :student";
        Query query = Main.session.createQuery(hql);
        query.setParameter("student", this);
        return (List<Course>) query.getResultList();
    }
    public List<Course> getAvailableCourses(){
        List<Course> registeredCourses = getRegisteredCourses();
        List<Course> availableCourses = Course.getAvailableCourses();
        for(Course course: registeredCourses)
            availableCourses.remove(course);
        return availableCourses;
    }
    public void registerCourse(int id){
        Course course = new Course();
        course.setId(id);
        registerCourse(course);
    }
    public void registerCourse(Course course) {
        Registration registration = new Registration();
        registration.setUser(this);
        registration.setCourse(course);
        try {
            Main.session.save(registration);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
                Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void dropCourse(int id) {
        Course course = new Course();
        course.setId(id);
        dropCourse(course);
    }
    public void dropCourse(Course course) {
        String hql = "FROM Registration WHERE user = :student AND course = :course";
        Query query = Main.session.createQuery(hql);
        query.setParameter("student", this);
        query.setParameter("course", course);
        List<Registration> registrationList = query.getResultList();
        Registration registration = registrationList.get(0);
        try {
            Main.transaction.begin();
            Main.session.delete(registration);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
                Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}