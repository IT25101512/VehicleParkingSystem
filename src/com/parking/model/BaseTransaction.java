package com.parking.model;

/**
 * BaseTransaction — Parent class (Inheritance).
 * Holds common fields shared by all transaction types.
 * Transaction.java extends this class.
 */
public class BaseTransaction {

    private String transactionId;
    private String vehicleId;
    private String slotId;
    private String userId;
    private String status; // ACTIVE, COMPLETED

    // No-arg constructor
    public BaseTransaction() {}

    // Parameterized constructor
    public BaseTransaction(String transactionId, String vehicleId,
                           String slotId, String userId, String status) {
        this.transactionId = transactionId;
        this.vehicleId     = vehicleId;
        this.slotId        = slotId;
        this.userId        = userId;
        this.status        = status;
    }

    // Base fee calculation — overridden in Transaction
    public double calculateFee() {
        return 50.0; // default base fee (Rs. 50 minimum)
    }

    // Getters & Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getSlotId() { return slotId; }
    public void setSlotId(String slotId) { this.slotId = slotId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}