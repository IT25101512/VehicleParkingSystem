package com.parking.service;

import com.parking.model.Admin;
import com.parking.util.FileHandler;
import javax.servlet.ServletContext;
import java.util.*;

public class AdminService {
    private static final String FILE = "admins.txt";

    private String path(ServletContext ctx) {
        return FileHandler.getDataPath(ctx, FILE);
    }

    // ── Queries ──────────────────────────────────────────────────────────────

    public List<Admin> getAllAdmins(ServletContext ctx) {
        List<Admin> admins = new ArrayList<>();
        for (String line : FileHandler.readLines(path(ctx))) {
            Admin a = Admin.fromString(line);
            if (a != null) admins.add(a);
        }
        return admins;
    }

    public Admin getAdminByUsername(ServletContext ctx, String username) {
        for (Admin a : getAllAdmins(ctx)) {
            if (a.getUsername().equals(username)) return a;
        }
        return null;
    }

    // ── Commands ─────────────────────────────────────────────────────────────

    /**
     * Seeds a default admin account if no admins exist yet.
     * Called once from AdminServlet.init() — not buried inside login().
     */
    public void seedDefaultAdmin(ServletContext ctx) {
        if (getAllAdmins(ctx).isEmpty()) {
            Admin defaultAdmin = new Admin(
                    "A001", "admin", "admin123", "System Admin", "admin@parking.com"
            );
            FileHandler.appendLine(path(ctx), defaultAdmin.toString());
        }
    }

    /**
     * Authenticates an admin by username and password.
     * Returns the Admin on success, null on failure.
     */
    public Admin login(ServletContext ctx, String username, String password) {
        for (Admin a : getAllAdmins(ctx)) {
            if (a.getUsername().equals(username)
                    && a.getPassword().equals(password)) return a;
        }
        return null;
    }

    /**
     * Adds a new admin account (for future admin-management features).
     */
    public boolean addAdmin(ServletContext ctx, Admin admin) {
        if (getAdminByUsername(ctx, admin.getUsername()) != null) return false; // no duplicates
        admin.setAdminId(FileHandler.generateId("A"));
        FileHandler.appendLine(path(ctx), admin.toString());
        return true;
    }
}