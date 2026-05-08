package com.parking.controller;

import com.parking.model.*;
import com.parking.service.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {

    private PaymentService paymentService;
    private TransactionService txnService;
    private VehicleService vehicleService;
    private SlotService slotService;

    @Override
    public void init() throws ServletException {
        paymentService  = new PaymentService();
        txnService      = new TransactionService();
        vehicleService  = new VehicleService();
        slotService     = new SlotService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();

        if ("/receipt".equals(action)) {
            showReceipt(req, resp);
        } else {
            // /payment/ or /payment/process (GET) → show payment form
            req.getRequestDispatcher("/pages/payment/payment.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();

        if ("/process".equals(action)) {
            processPayment(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // ── private helpers ──────────────────────────────────────────────────────

    private void showReceipt(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String paymentId = req.getParameter("paymentId");
        Payment payment  = paymentService.getPaymentById(getServletContext(), paymentId);

        if (payment == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Payment not found.");
            return;
        }

        Transaction txn = txnService.getTransactionById(getServletContext(), payment.getTransactionId());

        req.setAttribute("payment",     payment);
        req.setAttribute("transaction", txn);

        if (txn != null) {
            req.setAttribute("vehicle", vehicleService.getVehicleById(getServletContext(), txn.getVehicleId()));
            req.setAttribute("slot",    slotService.getSlotById(getServletContext(), txn.getSlotId()));
        }

        req.getRequestDispatcher("/pages/payment/receipt.jsp").forward(req, resp);
    }

    private void processPayment(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String transactionId = req.getParameter("transactionId");
        String method        = req.getParameter("paymentMethod"); // matches JSP field name
        double amount        = Double.parseDouble(req.getParameter("amount"));

        HttpSession session = req.getSession(false);
        String userId = (session != null && session.getAttribute("user") != null)
                ? ((User) session.getAttribute("user")).getUserId()
                : "GUEST";

        Payment payment = paymentService.processPayment(
                getServletContext(), transactionId, userId, amount, method
        );

        resp.sendRedirect(req.getContextPath() + "/payment/receipt?paymentId=" + payment.getPaymentId());
    }
}