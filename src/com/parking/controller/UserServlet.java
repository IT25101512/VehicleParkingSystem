package com.parking.controller;

import com.parking.model.User;
import com.parking.service.UserService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();   // correct servlet lifecycle
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "/login";

        switch (action) {
            case "/register": showRegister(req, resp); break;
            case "/login":    showLogin(req, resp);    break;
            case "/profile":  showProfile(req, resp);  break;
            case "/logout":   handleLogout(req, resp); break;
            default:          resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "";

        switch (action) {
            case "/register": handleRegister(req, resp); break;
            case "/login":    handleLogin(req, resp);    break;
            case "/update":   handleUpdate(req, resp);   break;
            default:          resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ── private: GET handlers ─────────────────────────────────────────────────

    private void showRegister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
    }

    private void showLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // If already logged in, skip the login page
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/vehicle/list");
            return;
        }
        req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
    }

    private void showProfile(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }
        // Expose user to request scope so JSP EL ${user.name} works
        // consistently whether arriving via GET or POST
        req.setAttribute("user", session.getAttribute("user"));
        req.getRequestDispatcher("/pages/user/profile.jsp").forward(req, resp);
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    // ── private: POST handlers ────────────────────────────────────────────────

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String name     = req.getParameter("name");
        String email    = req.getParameter("email");
        String password = req.getParameter("password");
        String phone    = req.getParameter("phone");
        String address  = req.getParameter("address");

        // Server-side validation (mirrors the front-end placeholder hint)
        if (password == null || password.length() < 6) {
            req.setAttribute("error", "Password must be at least 6 characters.");
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
            return;
        }

        User user = new User(null, name, email, password, phone, address);
        boolean success = userService.registerUser(getServletContext(), user);

        if (success) {
            req.setAttribute("success", "Registration successful! Please sign in.");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "That email address is already registered.");
            req.getRequestDispatcher("/pages/user/register.jsp").forward(req, resp);
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userService.login(getServletContext(), email, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);   // single source of truth
            // Route through the vehicle servlet, not directly to the JSP
            resp.sendRedirect(req.getContextPath() + "/vehicle/list");
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return;
        }

        User existing = (User) session.getAttribute("user");

        // Build an updated copy — do NOT mutate the session object before save
        User updated = new User(
                existing.getUserId(),
                req.getParameter("name"),
                existing.getEmail(),          // email is immutable
                existing.getPassword(),       // password unchanged via profile form
                req.getParameter("phone"),
                req.getParameter("address")
        );

        boolean saved = userService.updateUser(getServletContext(), updated);

        if (saved) {
            session.setAttribute("user", updated);   // update session only on success
            req.setAttribute("user", updated);
            req.setAttribute("success", "Profile updated successfully.");
        } else {
            req.setAttribute("user", existing);
            req.setAttribute("error", "Could not save changes. Please try again.");
        }

        req.getRequestDispatcher("/pages/user/profile.jsp").forward(req, resp);
    }
}