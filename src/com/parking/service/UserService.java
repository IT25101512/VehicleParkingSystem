package com.parking.service;

import com.parking.model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final String FILE_PATH = "web/data/users.txt";
    private int nextId = 1;

    public boolean register(String username, String password, String email, String role) {
        if (findByUsername(username) != null) return false;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(nextId + "," + username + "," + password + "," + email + "," + role + ",true");
            bw.newLine();
            nextId++;
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user != null && user.getPassword().equals(password)) return user;
        return null;
    }

    public User findByUsername(String username) {
        for (User u : getAllUsers()) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    users.add(new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5])));
                }
            }
        } catch (IOException e) {}
        return users;
    }

    public boolean deleteUser(String username) {
        List<User> users = getAllUsers();
        boolean removed = users.removeIf(u -> u.getUsername().equals(username));
        if (removed) saveAll(users);
        return removed;
    }

    public boolean updateUser(String username, String newEmail, String newPassword) {
        List<User> users = getAllUsers();
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                u.setEmail(newEmail);
                u.setPassword(newPassword);
                saveAll(users);
                return true;
            }
        }
        return false;
    }

    private void saveAll(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User u : users) {
                bw.write(u.getId() + "," + u.getUsername() + "," + u.getPassword() + "," + u.getEmail() + "," + u.getRole() + "," + u.isActive());
                bw.newLine();
            }
        } catch (IOException e) {}
    }
}