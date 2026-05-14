package com.parking.model;

public class Payment {
    private String paymentId;
    private String transactionId;
    private String userId;
    private double amount;
    private String paymentTime;
    private String method; // CASH, CARD, ONLINE
    private String status; // PAID, PENDING

    public Payment() {}

    public Payment(String paymentId, String transactionId, String userId,
                   double amount, String paymentTime, String method, String status) {
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.method = method;
        this.status = status;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getPaymentTime() { return paymentTime; }
    public void setPaymentTime(String paymentTime) { this.paymentTime = paymentTime; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return paymentId + "," + transactionId + "," + userId + "," + amount + ","
                + paymentTime + "," + method + "," + status;
    }

    public static Payment fromString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 7) return null;
        return new Payment(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
                parts[4], parts[5], parts[6]);
    }
}
