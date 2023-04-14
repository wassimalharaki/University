package com.example.university;



import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

//public class Student extends User {
//
//    private List<Course> registeredCourses = new ArrayList<>();
//
//    public Student() {}
//
//    public List<Course> viewCourses() {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        List<Course> courses = null;
//        try {
//            courses = session.createQuery("FROM Course", Course.class).list();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return courses;
//    }

//    public void registerCourse(Course course) {
//        if (!registeredCourses.contains(course)) {
//            registeredCourses.add(course);
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            Transaction tx = null;
//            try {
//                tx = session.beginTransaction();
//                session.update(this);
//                session.update(course);
//                tx.commit();
//            } catch (HibernateException e) {
//                if (tx != null) tx.rollback();
//                e.printStackTrace();
//            } finally {
//                session.close();
//            }
//        }
//    }
//
//    public void dropCourse(Course course) {
//        if (registeredCourses.contains(course)) {
//            registeredCourses.remove(course);
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            Transaction tx = null;
//            try {
//                tx = session.beginTransaction();
//                session.update(this);
//                session.update(course);
//                tx.commit();
//            } catch (HibernateException e) {
//                if (tx != null) tx.rollback();
//                e.printStackTrace();
//            } finally {
//                session.close();
//            }
//        }
//    }
//}
public class Student extends User {

    private List<Registration> registrations = new ArrayList<>();

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void register(Course course) {
        try {
            Registration registration = new Registration();
            registration.setUser(this);
            registration.setCourse(course);
            registrations.add(registration);
        } catch (Exception e) {
            System.out.println("Error registering for course: " + e.getMessage());
        } finally {
            System.out.println("Registration process completed.");
        }
    }

    public void drop(Course course) {
        try {
            for (Registration registration : registrations) {
                if (registration.getCourse().equals(course)) {
                    registrations.remove(registration);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error dropping course: " + e.getMessage());
        } finally {
            System.out.println("Dropping process completed.");
        }
    }

    public List<Course> viewCourses() {
        List<Course> courses = new ArrayList<>();
        try {
            for (Registration registration : registrations) {
                courses.add(registration.getCourse());
            }
        } catch (Exception e) {
            System.out.println("Error viewing courses: " + e.getMessage());
        } finally {
            System.out.println("Viewing process completed.");
        }
        return courses;
    }
}
