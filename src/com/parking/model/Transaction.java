package com.parking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Transaction — extends BaseTransaction (Inheritance).
 * Inherits transactionId, vehicleId, slotId, userId, status from BaseTransaction.
 * Adds entryTime and exitTime specific to a parking transaction.
 */
public class Transaction extends BaseTransaction {

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String entryTime;
    private String exitTime;

    // No-arg constructor — calls parent no-arg constructor
    public Transaction() {
        super();
    }

    // Full constructor — sends common fields to BaseTransaction via super()
    public Transaction(String transactionId, String vehicleId, String slotId,
                       String userId, String entryTime, String exitTime, String status) {
        super(transactionId, vehicleId, slotId, userId, status); // sends to BaseTransaction
        this.entryTime = entryTime;
        this.exitTime  = exitTime;
    }

    /**
     * Overrides BaseTransaction.calculateFee() — Method Overriding + Polymorphism.
     * Rs. 50 per hour, minimum 1 hour, based on entryTime and exitTime.
     */
    @Override
    public double calculateFee() {
        if (entryTime == null || entryTime.isEmpty()
                || exitTime == null || exitTime.isEmpty()) return 0;
        try {
            LocalDateTime entry   = LocalDateTime.parse(entryTime, FMT);
            LocalDateTime exitDt  = LocalDateTime.parse(exitTime,  FMT);
            long minutes = java.time.Duration.between(entry, exitDt).toMinutes();
            long hours   = (long) Math.ceil(minutes / 60.0);
            if (hours < 1) hours = 1;
            return hours * 50.0;
        } catch (Exception e) {
            return 50.0;
        }
    }

    // Getters & Setters for Transaction-specific fields
    public String getEntryTime() { return entryTime; }
    public void setEntryTime(String entryTime) { this.entryTime = entryTime; }

    public String getExitTime() { return exitTime; }
    public void setExitTime(String exitTime) { this.exitTime = exitTime; }

    /**
     * Converts Transaction object to CSV string for file storage.
     * Uses getters from BaseTransaction for inherited fields.
     */
    @Override
    public String toString() {
        return getTransactionId() + "," + getVehicleId() + "," + getSlotId() + ","
                + getUserId() + "," + entryTime + "," + exitTime + "," + getStatus();
    }

    /**
     * Creates a Transaction object from a CSV line read from file.
     */
    public static Transaction fromString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 7) return null;
        return new Transaction(parts[0], parts[1], parts[2], parts[3],
                parts[4], parts[5], parts[6]);
    }
}