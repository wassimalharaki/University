package com.example.university;

import org.hibernate.query.Query;

import java.util.List;

public class Student extends User {

    public List<Course> getRegisteredCourses() {
        String hql = "SELECT course From Registration WHERE user = :student";
        Query query = Main.session.createQuery(hql);
        query.setParameter("student", this);
        return (List<Course>) query.getResultList();
    }

    public void registerCourse(Course course) {
        Registration registration = new Registration();
        registration.setUser(this);
        registration.setCourse(course);
        try {
            Main.session.save(registration);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            if (Main.transaction != null) {
                if (!Main.transaction.getStatus().equals("COMMITTED"))
                    Main.transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
    }

    public void dropCourse(Course course) {
        String hql = "FROM Registration WHERE user = :student AND course = :course";
        Query query = Main.session.createQuery(hql);
        query.setParameter("student", this);
        query.setParameter("course", course);
        List<Registration> registrationList = query.getResultList();
        if (registrationList.size() != 1)
            System.out.println("ERROR IN HQL");
        Registration registration = registrationList.get(0);
        try {
            Main.session.delete(registration);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
