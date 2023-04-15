package com.example.university;

import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.mindrot.jbcrypt.BCrypt;

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
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRole(role);
        try {
            Main.session.save(user);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
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
            Main.transaction.begin();
            Main.session.delete(user);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
                Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(int id, String name, String email, String password, String role) {
        User user = new User();
        user.setId(id);
        updateUser(user, name, email, password, role);
    }
    public void updateUser(User user, String name, String email, String password, String role) {
        user.setName(name);
        user.setEmail(email);
        if (password.isEmpty()) {
            User userFromDB = Main.session.get(User.class, user.getId());
            user.setPassword(userFromDB.getPassword());
        }
        else
            user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRole(role);
        try {
            Main.transaction.begin();
            Main.session.update(user);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
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
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
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
            Main.transaction.begin();
            Main.session.delete(course);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
                Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void updateCourse(int id, String name, int instructorId, boolean available) {
        Course course = new Course();
        course.setId(id);
        User instructor = new User();
        instructor.setId(instructorId);
        updateCourse(course, name, instructor, available);
    }
    public void updateCourse(Course course, String name, User instructor, boolean available) {
        course.setName(name);
        course.setInstructor(instructor);
        course.setAvailable(available);
        try {
            Main.transaction.begin();
            Main.session.update(course);
            if (Main.transaction.getStatus().equals(TransactionStatus.ACTIVE))
                Main.transaction.commit();
        } catch (Exception e) {
            Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }
}