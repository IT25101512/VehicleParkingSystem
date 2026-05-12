package com.parking.model;

public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    public User() {}

    public User(String userId, String name, String email,
                String password, String phone, String address) {
        this.userId   = userId;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.phone    = phone;
        this.address  = address;
    }

    // Getters & Setters
    public String getUserId()  { return userId; }
    public void setUserId(String userId)   { this.userId = userId; }

    public String getName()    { return name; }
    public void setName(String name)       { this.name = name; }

    public String getEmail()   { return email; }
    public void setEmail(String email)     { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone()   { return phone; }
    public void setPhone(String phone)     { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    /**
     * Serialise to CSV. Address is kept last so any embedded commas
     * are absorbed by split(",", 6) on read.
     */
    @Override
    public String toString() {
        return userId + "," + name + "," + email + ","
                + password + "," + phone + "," + address;
    }

    /**
     * Limit split to 6 parts so an address containing commas
     * stays in parts[5] intact.
     */
    public static User fromString(String line) {
        String[] parts = line.split(",", 6);
        if (parts.length < 6) return null;
        return new User(parts[0], parts[1], parts[2],
                parts[3], parts[4], parts[5]);
    }
}