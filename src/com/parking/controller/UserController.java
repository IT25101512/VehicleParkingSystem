package com.parking.controller;

import com.parking.model.User;
import com.parking.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/user/*")
public class UserController extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action.equals("/register")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            boolean success = userService.register(username, password, email, role);
            if (success) {
                response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            } else {
                request.setAttribute("error", "Username already exists!");
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            }
        }

        if (action.equals("/login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.login(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                response.sendRedirect(request.getContextPath() + "/pages/userList.jsp");
            } else {
                request.setAttribute("error", "Invalid username or password!");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            }
        }

        if (action.equals("/update")) {
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            userService.updateUser(username, email, password);
            response.sendRedirect(request.getContextPath() + "/pages/userList.jsp");
        }

        if (action.equals("/delete")) {
            String username = request.getParameter("username");
            userService.deleteUser(username);
            response.sendRedirect(request.getContextPath() + "/pages/userList.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action.equals("/list")) {
            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/pages/userList.jsp").forward(request, response);
        }
    }
}