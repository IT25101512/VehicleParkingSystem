package com.parking.service;

import com.parking.model.Transaction;
import com.parking.util.FileHandler;
import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * TransactionService — implements ITransactionService (Interface).
 * Contains all CRUD operations for transactions.
 * All logic stays exactly the same as the original code.
 */
public class TransactionService implements ITransactionService {

    private static final String FILE = "transactions.txt";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String path(ServletContext ctx) {
        return FileHandler.getDataPath(ctx, FILE);
    }

    // READ — Get all transactions from file
    @Override
    public List<Transaction> getAllTransactions(ServletContext ctx) {
        List<Transaction> txns = new ArrayList<>();
        for (String line : FileHandler.readLines(path(ctx))) {
            Transaction t = Transaction.fromString(line);
            if (t != null) txns.add(t);
        }
        return txns;
    }

    // READ — Get single transaction by ID
    @Override
    public Transaction getTransactionById(ServletContext ctx, String txnId) {
        for (Transaction t : getAllTransactions(ctx)) {
            if (t.getTransactionId().equals(txnId)) return t;
        }
        return null;
    }

    // READ — Get active (ongoing) transaction for a vehicle
    @Override
    public Transaction getActiveTransactionByVehicle(ServletContext ctx, String vehicleId) {
        for (Transaction t : getAllTransactions(ctx)) {
            if (t.getVehicleId().equals(vehicleId) && "ACTIVE".equals(t.getStatus()))
                return t;
        }
        return null;
    }

    // READ — Get all transactions belonging to a user
    @Override
    public List<Transaction> getTransactionsByUser(ServletContext ctx, String userId) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : getAllTransactions(ctx)) {
            if (t.getUserId().equals(userId)) result.add(t);
        }
        return result;
    }

    // CREATE — Record vehicle entry, generate transaction
    @Override
    public Transaction createEntry(ServletContext ctx, String vehicleId,
                                   String slotId, String userId) {
        String now = LocalDateTime.now().format(FMT);
        Transaction t = new Transaction(
                FileHandler.generateId("T"), vehicleId, slotId, userId, now, "", "ACTIVE"
        );
        FileHandler.appendLine(path(ctx), t.toString());
        return t;
    }

    // UPDATE — Record vehicle exit, mark transaction COMPLETED
    @Override
    public Transaction recordExit(ServletContext ctx, String transactionId) {
        Transaction t = getTransactionById(ctx, transactionId);
        if (t == null || !"ACTIVE".equals(t.getStatus())) return null;
        t.setExitTime(LocalDateTime.now().format(FMT));
        t.setStatus("COMPLETED");
        updateTransaction(ctx, t);
        return t;
    }

    // UPDATE — Rewrite updated transaction record in file
    @Override
    public boolean updateTransaction(ServletContext ctx, Transaction updated) {
        List<Transaction> all = getAllTransactions(ctx);
        List<String> lines = new ArrayList<>();
        boolean found = false;
        for (Transaction t : all) {
            if (t.getTransactionId().equals(updated.getTransactionId())) {
                lines.add(updated.toString());
                found = true;
            } else {
                lines.add(t.toString());
            }
        }
        if (found) FileHandler.writeLines(path(ctx), lines);
        return found;
    }

    /**
     * Fee calculation — delegates to Transaction.calculateFee() (Polymorphism).
     * Transaction overrides BaseTransaction.calculateFee() with time-based logic.
     * Rs. 50 per hour, minimum 1 hour.
     */
    @Override
    public double calculateFee(Transaction t) {
        return t.calculateFee(); // calls overridden method in Transaction
    }
}