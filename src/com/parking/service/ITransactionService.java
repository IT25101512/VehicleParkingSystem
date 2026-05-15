package com.parking.service;

import com.parking.model.Transaction;
import javax.servlet.ServletContext;
import java.util.List;

/**
 * ITransactionService — Interface (Abstraction).
 * Defines the contract for all transaction operations.
 * TransactionService implements this interface.
 */
public interface ITransactionService {
    List<Transaction> getAllTransactions(ServletContext ctx);
    Transaction getTransactionById(ServletContext ctx, String txnId);
    Transaction getActiveTransactionByVehicle(ServletContext ctx, String vehicleId);
    List<Transaction> getTransactionsByUser(ServletContext ctx, String userId);
    Transaction createEntry(ServletContext ctx, String vehicleId, String slotId, String userId);
    Transaction recordExit(ServletContext ctx, String transactionId);
    boolean updateTransaction(ServletContext ctx, Transaction updated);
    double calculateFee(Transaction t);
}