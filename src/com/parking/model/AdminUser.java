package com.parking.model;

public class AdminUser extends User {
    private String adminCode;

    public AdminUser(int id, String username, String password, String email, String adminCode) {
        super(id, username, password, email, "ADMIN", true);
        this.adminCode = adminCode;
    }

    public String getAdminCode() { return adminCode; }
    public void setAdminCode(String adminCode) { this.adminCode = adminCode; }
}