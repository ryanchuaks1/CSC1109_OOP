package com.rjdxbanking.rjdxbank.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Models.CreateIncomingTransaction;

public class IncomingTransactionService {
    Firestore db = FirestoreClient.getFirestore();

    public void createTransaction(CreateIncomingTransaction transaction) {
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("transactions").document().set(transaction);
            System.out.printf("Successfully created transaction at %s \n", apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
