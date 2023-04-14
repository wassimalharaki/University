package com.example.university;

public class Admin extends User {

    public void addUser(String name, String email, String password, String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        try {
            Main.session.save(user);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            if (Main.transaction != null)
                if (!Main.transaction.getStatus().equals("COMMITTED"))
                    Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void removeUser(User user) {
        try {
            Main.session.delete(user);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUser(int id) {
        User user = new User();
        user.setId(id);
        try {
            Main.session.delete(user);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
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
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            if (Main.transaction != null)
                if (!Main.transaction.getStatus().equals("COMMITTED"))
                    Main.transaction.rollback();
            System.out.println(e.getMessage());
        }
    }

    public void removeCourse(Course course) {
        try {
            Main.session.delete(course);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeCourse(int id) {
        Course course = new Course();
        course.setId(id);
        try {
            Main.session.delete(course);
            if (!Main.transaction.getStatus().equals("COMMITTED"))
                Main.transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}