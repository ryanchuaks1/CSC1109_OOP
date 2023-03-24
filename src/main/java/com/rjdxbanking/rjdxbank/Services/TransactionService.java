package com.rjdxbanking.rjdxbank.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Models.CreateTransaction;
import com.rjdxbanking.rjdxbank.Models.TransactionType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    Firestore db = FirestoreClient.getFirestore();

    public Transaction getTransactionById(String transactionId) {
        Transaction transaction = null;
        try {
            ApiFuture<DocumentSnapshot> apiFuture = db.collection("transactions").document(transactionId).get();
            transaction = apiFuture.get().toObject(Transaction.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transaction;
    }

    public void createTransaction(CreateTransaction createTransaction) {
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("transactions").document().set(createTransaction);
            System.out.printf("Successfully created transaction at %s \n", apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        List<Transaction> transactions = new ArrayList<Transaction>();
        try {
            var apiFuture = db.collection("transactions").whereEqualTo("accountId", accountId).get();
            QuerySnapshot snapshots = apiFuture.get();
            transactions = snapshots.toObjects(Transaction.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }

    public ObservableList<Transaction> getTransactionsByAccountIdLimit100(String accountId) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try {
            var apiFuture = db.collection("transactions").whereEqualTo("accountId", accountId).limit(100).get();
            QuerySnapshot snapshots = apiFuture.get();
            var transactionsList = snapshots.toObjects(Transaction.class);
            transactions.addAll(transactionsList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }

    public List<Transaction> getWithdrawTransactionsToday(String accountId, TransactionType type) {
        List<Transaction> transactions = new ArrayList<Transaction>();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formatDateTime = now.format(format);
        try {
            var apiFuture = db.collection("transactions").whereEqualTo("accountId", accountId)
                    .whereEqualTo("transactionType", type)
                    .whereGreaterThanOrEqualTo("timeStamp", formatDateTime)
                    .whereLessThanOrEqualTo("timeStamp", formatDateTime + '~').get();

            QuerySnapshot snapshots = apiFuture.get();
            var transactionsList = snapshots.toObjects(Transaction.class);
            transactions.addAll(transactionsList);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }
}