package com.parking.model;

/**
 * Read-only view object used by history.jsp.
 * Combines Transaction data with resolved licensePlate and calculated fee,
 * so the JSP never needs to call services or do calculations itself.
 */
public class TransactionView {
    private final Transaction transaction;
    private final String licensePlate;
    private final double fee;

    public TransactionView(Transaction transaction, String licensePlate, double fee) {
        this.transaction  = transaction;
        this.licensePlate = licensePlate;
        this.fee          = fee;
    }

    // Delegate Transaction fields — JSP uses ${t.transactionId} etc.
    public String getTransactionId() { return transaction.getTransactionId(); }
    public String getVehicleId()     { return transaction.getVehicleId(); }
    public String getSlotId()        { return transaction.getSlotId(); }
    public String getEntryTime()     { return transaction.getEntryTime(); }
    public String getExitTime()      { return transaction.getExitTime(); }
    public String getStatus()        { return transaction.getStatus(); }

    // Extra fields the JSP needs
    public String getLicensePlate()  { return licensePlate; }
    public double getFee()           { return fee; }
}