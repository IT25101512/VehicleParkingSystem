package com.parking.controller;

import com.parking.model.*;
import com.parking.service.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {
    private final PaymentService paymentService = new PaymentService();
    private final TransactionService txnService = new TransactionService();
    private final VehicleService vehicleService = new VehicleService();
    private final SlotService slotService = new SlotService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/receipt".equals(action)) {
            String paymentId = req.getParameter("paymentId");
            for (Payment p : paymentService.getAllPayments(getServletContext())) {
                if (p.getPaymentId().equals(paymentId)) {
                    req.setAttribute("payment", p);
                    Transaction t = txnService.getTransactionById(getServletContext(), p.getTransactionId());
                    req.setAttribute("transaction", t);
                    if (t != null) {
                        req.setAttribute("vehicle", vehicleService.getVehicleById(getServletContext(), t.getVehicleId()));
                        req.setAttribute("slot", slotService.getSlotById(getServletContext(), t.getSlotId()));
                    }
                    break;
                }
            }
            req.getRequestDispatcher("/pages/payment/receipt.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/pages/payment/payment.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String transactionId = req.getParameter("transactionId");
        String method = req.getParameter("method");
        double amount = Double.parseDouble(req.getParameter("amount"));
        HttpSession session = req.getSession(false);
        String userId = session != null && session.getAttribute("user") != null
                ? ((User) session.getAttribute("user")).getUserId() : "GUEST";

        Payment p = paymentService.processPayment(getServletContext(), transactionId, userId, amount, method);
        resp.sendRedirect(req.getContextPath() + "/payment/receipt?paymentId=" + p.getPaymentId());
    }
}
