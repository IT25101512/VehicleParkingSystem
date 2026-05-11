package com.parking.controller;

import com.parking.model.User;
import com.parking.model.Vehicle;
import com.parking.service.IVehicleService;
import com.parking.service.VehicleService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

/**
 * VehicleServlet — Controller in MVC pattern.
 * Uses IVehicleService interface type for Polymorphism.
 * All original logic stays exactly the same.
 */
@WebServlet("/vehicle/*")
public class VehicleServlet extends HttpServlet {


    // Polymorphism — interface type holding concrete implementation
    private final IVehicleService vehicleService = new VehicleService();

    private boolean checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) return;
        String action = req.getPathInfo();
        if (action == null) action = "/list";

        switch (action) {
            case "/add":
                req.getRequestDispatcher("/pages/vehicle/add.jsp").forward(req, resp);
                break;
            case "/list":
                User user = (User) req.getSession().getAttribute("user");
                List<Vehicle> vehicles = vehicleService.getVehiclesByUser(getServletContext(), user.getUserId());
                req.setAttribute("vehicles", vehicles);
                req.getRequestDispatcher("/pages/vehicle/list.jsp").forward(req, resp);
                break;
            case "/search":
                req.getRequestDispatcher("/pages/vehicle/search.jsp").forward(req, resp);
                break;
            case "/delete":
                String delId = req.getParameter("id");
                vehicleService.deleteVehicle(getServletContext(), delId);
                resp.sendRedirect(req.getContextPath() + "/vehicle/list");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/vehicle/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!checkLogin(req, resp)) return;
        String action = req.getPathInfo();
        if (action == null) action = "";

        if (action.equals("/add")) {
            User user = (User) req.getSession().getAttribute("user");
            Vehicle v = new Vehicle(null,
                    req.getParameter("plateNumber"),
                    req.getParameter("type"),
                    user.getUserId(),
                    req.getParameter("model"),
                    req.getParameter("color"));
            boolean ok = vehicleService.addVehicle(getServletContext(), v);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/vehicle/list");
            } else {
                req.setAttribute("error", "Plate number already registered.");
                req.getRequestDispatcher("/pages/vehicle/add.jsp").forward(req, resp);
            }
        } else if (action.equals("/search")) {
            String plate = req.getParameter("plate");
            Vehicle v = vehicleService.getVehicleByPlate(getServletContext(), plate);
            req.setAttribute("result", v);
            req.getRequestDispatcher("/pages/vehicle/search.jsp").forward(req, resp);
        }
    }
}