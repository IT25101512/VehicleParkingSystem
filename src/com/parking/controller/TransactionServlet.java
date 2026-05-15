package com.parking.controller;

import com.parking.model.*;
import com.parking.service.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * TransactionServlet — Controller in MVC pattern.
 * Uses ITransactionService interface type for Polymorphism.
 * Bug fixes applied:
 *   1. Fixed broken plate resolution logic in showHistory()
 *   2. Fixed wrong method call: slotService.getSlotService() → slotService.setSlotStatus()
 */
@WebServlet("/transaction/*")
public class TransactionServlet extends HttpServlet {

    // Polymorphism — interface type holding concrete implementation
    private ITransactionService txnService;
    private SlotService         slotService;
    private IVehicleService     vehicleService;

    @Override
    public void init() throws ServletException {
        txnService     = new TransactionService();
        slotService    = new SlotService();
        vehicleService = new VehicleService();
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "/history";

        switch (action) {
            case "/entry":   showEntryForm(req, resp);  break;
            case "/exit":    showExitForm(req, resp);   break;
            case "/history": showHistory(req, resp);    break;
            default:         resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();
        if (action == null) action = "";

        switch (action) {
            case "/entry": handleEntry(req, resp); break;
            case "/exit":  handleExit(req, resp);  break;
            default:       resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ── private: GET handlers ─────────────────────────────────────────────────

    private void showEntryForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/pages/transaction/entry.jsp").forward(req, resp);
    }

    private void showExitForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/pages/transaction/exit.jsp").forward(req, resp);
    }

    private void showHistory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        List<Transaction> raw = null;

        if (session != null && session.getAttribute("admin") != null) {
            raw = txnService.getAllTransactions(getServletContext());
            req.setAttribute("admin", session.getAttribute("admin"));
        } else if (session != null && session.getAttribute("user") != null) {
            User u = (User) session.getAttribute("user");
            raw = txnService.getTransactionsByUser(getServletContext(), u.getUserId());
        }

        // Build TransactionView list so JSP gets licensePlate + fee without logic
        List<TransactionView> views = new ArrayList<>();
        if (raw != null) {
            for (Transaction t : raw) {
                Vehicle v = vehicleService.getVehicleById(getServletContext(), t.getVehicleId());

                // BUG FIX 1: was → String plate if (v != null) plate = String.valueOf(v.getClass());
                // Fixed  → properly get plate number from vehicle object
                String plate = (v != null) ? v.getPlateNumber() : "Unknown";

                // Polymorphism — calculateFee() calls Transaction's overridden method
                double fee = "COMPLETED".equals(t.getStatus()) ? txnService.calculateFee(t) : 0;
                views.add(new TransactionView(t, plate, fee));
            }
        }

        req.setAttribute("transactions", views);
        req.getRequestDispatcher("/pages/transaction/history.jsp").forward(req, resp);
    }

    // ── private: POST handlers ────────────────────────────────────────────────

    private void handleEntry(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String licensePlate = req.getParameter("licensePlate");
        String slotId       = req.getParameter("slotId");

        HttpSession session = req.getSession(false);
        String userId = (session != null && session.getAttribute("user") != null)
                ? ((User) session.getAttribute("user")).getUserId()
                : "GUEST";

        // Resolve vehicle by license plate
        Vehicle vehicle = vehicleService.getVehicleByPlate(getServletContext(), licensePlate);
        if (vehicle == null) {
            req.setAttribute("error", "No registered vehicle found with plate: " + licensePlate);
            req.getRequestDispatcher("/pages/transaction/entry.jsp").forward(req, resp);
            return;
        }

        // Guard: vehicle already has an active transaction
        if (txnService.getActiveTransactionByVehicle(getServletContext(), vehicle.getVehicleId()) != null) {
            req.setAttribute("error", "Vehicle " + licensePlate + " is already parked.");
            req.getRequestDispatcher("/pages/transaction/entry.jsp").forward(req, resp);
            return;
        }

        // CREATE — record entry transaction
        Transaction t = txnService.createEntry(getServletContext(), vehicle.getVehicleId(), slotId, userId);
        slotService.setSlotStatus(getServletContext(), slotId, "OCCUPIED");

        req.setAttribute("transaction", t);
        req.setAttribute("success", "Entry recorded. Transaction ID: " + t.getTransactionId());
        req.getRequestDispatcher("/pages/transaction/entry.jsp").forward(req, resp);
    }

    private void handleExit(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String txnId  = req.getParameter("transactionId");
        Transaction t = txnService.recordExit(getServletContext(), txnId);

        if (t == null) {
            req.setAttribute("error", "Transaction not found or already completed.");
            req.getRequestDispatcher("/pages/transaction/exit.jsp").forward(req, resp);
            return;
        }

        // BUG FIX 2: was → slotService.getSlotService(...) — wrong method name
        // Fixed  → slotService.setSlotStatus(...) — correct method to free the slot
        slotService.setSlotStatus(getServletContext(), t.getSlotId(), "AVAILABLE");

        // Polymorphism — calculateFee() calls Transaction's overridden method internally
        double fee = txnService.calculateFee(t);
        req.setAttribute("transaction",   t);
        req.setAttribute("transactionId", t.getTransactionId());
        req.setAttribute("fee",           fee);

        req.getRequestDispatcher("/pages/payment/payment.jsp").forward(req, resp);
    }
}