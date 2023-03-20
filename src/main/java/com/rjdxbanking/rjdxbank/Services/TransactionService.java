package com.rjdxbanking.rjdxbank.Services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.Transaction;
import com.rjdxbanking.rjdxbank.Models.CreateTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    Firestore db = FirestoreClient.getFirestore();
    ObjectMapper mapper = new ObjectMapper();
    

    public Transaction getTransactionById(String transactionId) {
        Transaction transaction = null;
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            var apiFuture = db.collection("transactions").document(transactionId).get();
                DocumentSnapshot snapshots = apiFuture.get();
                var transactionData = snapshots.getData();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String dateTime = (String)transactionData.get("timeStamp");
                LocalDateTime localDate = LocalDateTime.parse(dateTime,dtf);
                transactionData.replace("timeStamp", localDate);
                transactionData.replace("Id",transactionId);
                transaction = mapper.convertValue(transactionData, Transaction.class);
                
                // transaction = (Transaction) transactionData;
                
            // transaction = apiFuture.get().toObject(Transaction.class);
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
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            var apiFuture = db.collection("transactions").whereEqualTo("accountId", accountId).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (!snapshots.getDocuments().isEmpty()) {
                for(int i =0; i <snapshots.size(); i ++){
                    var transactionSnapshot = snapshots.getDocuments().get(i);
                    var transactionData = transactionSnapshot.getData();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String dateTime = (String)transactionData.get("timeStamp");
                    LocalDateTime localDate = LocalDateTime.parse(dateTime,dtf);
                    transactionData.replace("timeStamp", localDate);
                    Transaction trans = mapper.convertValue(transactionData, Transaction.class);
                    transactions.add(trans);
                }
            }
            
            // transactions = apiFuture.get().toObjects(Transaction.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }

    public ObservableList<Transaction> getTransactionsByAccountIdLimit100(String accountId) {
        ObservableList<Transaction> transactions = FXCollections.observableArrayList();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        try {
            var apiFuture = db.collection("transactions").limit(100).whereEqualTo("accountId", accountId).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (!snapshots.getDocuments().isEmpty()) {
                for(int i =0; i <snapshots.size(); i ++){
                    var transactionSnapshot = snapshots.getDocuments().get(i);
                    var transactionData = transactionSnapshot.getData();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String dateTime = (String)transactionData.get("timeStamp");
                    LocalDateTime localDate = LocalDateTime.parse(dateTime,dtf);
                    transactionData.replace("timeStamp", localDate);
                    Transaction trans = mapper.convertValue(transactionData, Transaction.class);
                    // Transaction trans = (Transaction) transactionData;
                    
                    transactions.add(trans);
                }
            }
                
            // List transactionListDB = apiFuture.get().toObjects(Transaction.class);
            
            // transactions.addAll(transactionListDB);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }
}