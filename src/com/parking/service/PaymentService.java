package com.parking.service;

import com.parking.model.Payment;
import com.parking.util.FileHandler;
import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaymentService {
    private static final String FILE = "payments.txt";
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String path(ServletContext ctx) {
        return FileHandler.getDataPath(ctx, FILE);
    }

    public List<Payment> getAllPayments(ServletContext ctx) {
        List<Payment> payments = new ArrayList<>();
        for (String line : FileHandler.readLines(path(ctx))) {
            Payment p = Payment.fromString(line);
            if (p != null) payments.add(p);
        }
        return payments;
    }

    public Payment getPaymentById(ServletContext ctx, String paymentId) {
        for (Payment p : getAllPayments(ctx)) {
            if (p.getPaymentId().equals(paymentId)) return p;
        }
        return null;
    }

    public Payment getPaymentByTransaction(ServletContext ctx, String transactionId) {
        for (Payment p : getAllPayments(ctx)) {
            if (p.getTransactionId().equals(transactionId)) return p;
        }
        return null;
    }

    public List<Payment> getPaymentsByUser(ServletContext ctx, String userId) {
        List<Payment> result = new ArrayList<>();
        for (Payment p : getAllPayments(ctx)) {
            if (p.getUserId().equals(userId)) result.add(p);
        }
        return result;
    }

    public Payment processPayment(ServletContext ctx, String transactionId,
                                  String userId, double amount, String method) {
        String now = LocalDateTime.now().format(FMT);
        Payment p = new Payment(
                FileHandler.generateId("P"), transactionId, userId,
                amount, now, method, "PAID"
        );
        FileHandler.appendLine(path(ctx), p.toString());
        return p;
    }

    public double getTotalRevenue(ServletContext ctx) {
        return getAllPayments(ctx).stream()
                .filter(p -> "PAID".equals(p.getStatus()))
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}