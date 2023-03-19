package com.rjdxbanking.rjdxbank.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Models.CreateTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TransactionService {
    Firestore db = FirestoreClient.getFirestore();

    public Transaction getTransactionById(String transactionId) {
        Transaction transaction = null;
        try {
            var apiFuture = db.collection("transactions").document(transactionId).get();
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
        List<Transaction> transactions = null;
        try {
            var apiFuture = db.collection("transactions").whereEqualTo("accountId", accountId).get();
            transactions = apiFuture.get().toObjects(Transaction.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }

    public ObservableList<Transaction> getTransactionsByAccountIdLimit100(String accountId) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        try {
            var apiFuture = db.collection("transactions").limit(100).whereEqualTo("accountId", accountId).get();
            List<Transaction> transactionListDB = apiFuture.get().toObjects(Transaction.class);
            transactions.addAll(transactionListDB);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }
}