package com.parking.controller;

import com.parking.model.*;
import com.parking.service.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {

    private AdminService       adminService;
    private UserService        userService;
    private SlotService        slotService;
    private TransactionService txnService;
    private PaymentService     paymentService;
    private VehicleService     vehicleService;

    // ── Lifecycle ─────────────────────────────────────────────────────────────

    @Override
    public void init() throws ServletException {
        adminService   = new AdminService();
        userService    = new UserService();
        slotService    = new SlotService();
        txnService     = new TransactionService();
        paymentService = new PaymentService();
        vehicleService = new VehicleService();

        // Seed default admin on first startup — not inside login()
        adminService.seedDefaultAdmin(getServletContext());
    }

    // ── Auth guard ────────────────────────────────────────────────────────────

    /**
     * Returns true if a valid admin session exists.
     * On failure, redirects to the login page and returns false.
     */
    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("admin") == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return false;
        }
        return true;
    }

    /**
     * Exposes the admin object to request scope so JSP EL
     * ${admin.name} works on every page consistently.
     */
    private void exposeAdmin(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("admin") != null) {
            req.setAttribute("admin", s.getAttribute("admin"));
        }
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "/dashboard";

        switch (action) {

            case "/login":
                // Already logged in → skip login page
                HttpSession existing = req.getSession(false);
                if (existing != null && existing.getAttribute("admin") != null) {
                    resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                    return;
                }
                req.getRequestDispatcher("/pages/admin/login.jsp").forward(req, resp);
                break;

            case "/logout":
                HttpSession s = req.getSession(false);
                if (s != null) s.invalidate();
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                break;

            case "/dashboard":
                if (!checkAdmin(req, resp)) return;
                exposeAdmin(req);
                loadDashboard(req, resp);
                break;

            case "/manage":
                if (!checkAdmin(req, resp)) return;
                exposeAdmin(req);
                loadManage(req, resp);
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "";

        switch (action) {

            case "/login":
                handleLogin(req, resp);
                break;

            case "/addSlot":
                if (!checkAdmin(req, resp)) return;
                handleAddSlot(req, resp);
                break;

            // Deletes are POST — GET must never mutate data
            case "/deleteUser":
                if (!checkAdmin(req, resp)) return;
                userService.deleteUser(getServletContext(), req.getParameter("id"));
                resp.sendRedirect(req.getContextPath() + "/admin/manage");
                break;

            case "/deleteSlot":
                if (!checkAdmin(req, resp)) return;
                slotService.deleteSlot(getServletContext(), req.getParameter("id"));
                resp.sendRedirect(req.getContextPath() + "/admin/manage");
                break;

            case "/deleteVehicle":
                if (!checkAdmin(req, resp)) return;
                vehicleService.deleteVehicle(getServletContext(), req.getParameter("id"));
                resp.sendRedirect(req.getContextPath() + "/admin/manage");
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        }
    }

    // ── private: GET handlers ─────────────────────────────────────────────────

    private void loadDashboard(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Fetch transaction list once — reuse for both count and recent list
        List<Transaction> allTxns = txnService.getAllTransactions(getServletContext());

        req.setAttribute("totalUsers",        userService.getAllUsers(getServletContext()).size());
        req.setAttribute("totalSlots",        slotService.getAllSlots(getServletContext()).size());
        req.setAttribute("availableSlots",    slotService.countAvailable(getServletContext()));
        req.setAttribute("occupiedSlots",     slotService.countOccupied(getServletContext()));
        req.setAttribute("totalTransactions", allTxns.size());
        req.setAttribute("totalRevenue",      paymentService.getTotalRevenue(getServletContext()));
        req.setAttribute("recentTransactions", allTxns);

        req.getRequestDispatcher("/pages/admin/dashboard.jsp").forward(req, resp);
    }

    private void loadManage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("users",    userService.getAllUsers(getServletContext()));
        req.setAttribute("slots",    slotService.getAllSlots(getServletContext()));
        req.setAttribute("vehicles", vehicleService.getAllVehicles(getServletContext()));
        req.getRequestDispatcher("/pages/admin/manage.jsp").forward(req, resp);
    }

    // ── private: POST handlers ────────────────────────────────────────────────

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Admin admin = adminService.login(getServletContext(), username, password);

        if (admin != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("admin", admin);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } else {
            req.setAttribute("error", "Invalid admin credentials.");
            req.getRequestDispatcher("/pages/admin/login.jsp").forward(req, resp);
        }
    }

    private void handleAddSlot(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        ParkingSlot slot = new ParkingSlot(
                null,
                req.getParameter("slotNumber"),
                req.getParameter("type"),
                "AVAILABLE",
                req.getParameter("floor")
        );
        slotService.addSlot(getServletContext(), slot);
        resp.sendRedirect(req.getContextPath() + "/admin/manage");
    }
}