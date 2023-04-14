package com.example.university;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private List<Course> registeredCourses = new ArrayList<>();

    public Student() {}

    public List<Course> viewCourses() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Course> courses = null;
        try {
            courses = session.createQuery("FROM Course", Course.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courses;
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course)) {
            registeredCourses.add(course);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(this);
                session.update(course);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.update(this);
                session.update(course);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }
}
