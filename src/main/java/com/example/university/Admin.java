package com.example.university;

import org.hibernate.query.Query;

import java.util.List;

public class Admin extends User {

    public List<User> getUsers() {
        String hql = "From User";
        Query query = Main.session.createQuery(hql);
        return (List<User>) query.getResultList();
    }

    private List<User> getSpecificUsers(String role) {
        String hql = "FROM User WHERE role = :role";
        Query query = Main.session.createQuery(hql);
        query.setParameter("role", role);
        return (List<User>) query.getResultList();
    }

    public List<User> getAdmins() {
        return getSpecificUsers("a");
    }
    public List<User> getStudents() {
        return getSpecificUsers("s");
    }
    public List<User> getInstructors() {
        return getSpecificUsers("i");
    }

    public List<Registration> getRegistrations() {
        String hql = "FROM Registration";
        Query query = Main.session.createQuery(hql);
        return (List<Registration>) query.getResultList();
    }

    public void addUser(String name, String email, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        try {
            Main.session.save(user);
            Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void removeUser(int id) {
        User user = new User();
        user.setId(id);
        removeUser(user);
    }
    public void removeUser(User user) {
        try {
            Main.session.delete(user);
            Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void addCourse(String name, User instructor, boolean available) {
        Course course = new Course();
        course.setName(name);
        course.setInstructor(instructor);
        course.setAvailable(available);
        try {
            Main.session.save(course);
            Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void removeCourse(int id) {
        Course course = new Course();
        course.setId(id);
        removeCourse(course);
    }
    public void removeCourse(Course course) {
        try {
            Main.session.delete(course);
            Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}