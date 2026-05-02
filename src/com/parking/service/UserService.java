package com.parking.service;

import com.parking.model.User;
import com.parking.util.FileHandler;
import javax.servlet.ServletContext;
import java.util.*;

public class UserService {
    private static final String FILE = "users.txt";

    private String path(ServletContext ctx) {
        return FileHandler.getDataPath(ctx, FILE);
    }

    // ── Queries ──────────────────────────────────────────────────────────────

    public List<User> getAllUsers(ServletContext ctx) {
        List<User> users = new ArrayList<>();
        for (String line : FileHandler.readLines(path(ctx))) {
            User u = User.fromString(line);
            if (u != null) users.add(u);
        }
        return users;
    }

    public User getUserById(ServletContext ctx, String userId) {
        for (User u : getAllUsers(ctx)) {
            if (u.getUserId().equals(userId)) return u;
        }
        return null;
    }

    public User getUserByEmail(ServletContext ctx, String email) {
        for (User u : getAllUsers(ctx)) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    // ── Commands ─────────────────────────────────────────────────────────────

    /**
     * Registers a new user. Returns false if the email is already taken.
     */
    public boolean registerUser(ServletContext ctx, User user) {
        if (getUserByEmail(ctx, user.getEmail()) != null) return false;
        user.setUserId(FileHandler.generateId("U"));
        FileHandler.appendLine(path(ctx), user.toString());
        return true;
    }

    /**
     * Authenticates a user by email and password.
     * Returns the User on success, null on failure.
     */
    public User login(ServletContext ctx, String email, String password) {
        User user = getUserByEmail(ctx, email);
        if (user != null && user.getPassword().equals(password)) return user;
        return null;
    }

    /**
     * Overwrites the stored record for the given user.
     * Only name, phone, and address may be changed here (email is immutable).
     */
    public boolean updateUser(ServletContext ctx, User updated) {
        List<User> all   = getAllUsers(ctx);
        List<String> lines = new ArrayList<>();
        boolean found    = false;

        for (User u : all) {
            if (u.getUserId().equals(updated.getUserId())) {
                lines.add(updated.toString());
                found = true;
            } else {
                lines.add(u.toString());
            }
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }

    /**
     * Removes a user record entirely (admin use).
     */
    public boolean deleteUser(ServletContext ctx, String userId) {
        List<User> all   = getAllUsers(ctx);
        List<String> lines = new ArrayList<>();
        boolean found    = false;

        for (User u : all) {
            if (u.getUserId().equals(userId)) {
                found = true;          // skip — effectively deletes
            } else {
                lines.add(u.toString());
            }
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }
}