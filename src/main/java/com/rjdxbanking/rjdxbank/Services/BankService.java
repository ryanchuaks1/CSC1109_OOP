package com.rjdxbanking.rjdxbank.Services;

import java.util.List;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.Bank;

public class BankService {
    Firestore db = FirestoreClient.getFirestore();

    public List<Bank> getBanks() {
        List<Bank> banks = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("banks").get();
            banks = apiFuture.get().toObjects(Bank.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return banks;
    }

    public Bank getBankById(String bankId) {
        Bank bank = null;
        try {

            ApiFuture<DocumentSnapshot> apiFuture = db.collection("banks").document(bankId).get();
            bank = apiFuture.get().toObject(Bank.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return bank;
    }

    public Bank getBankByRoute(String route) {
        Bank bank = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("banks").whereEqualTo("bankRoute", route).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (!snapshots.getDocuments().isEmpty()) {
                var bankSnapshot = snapshots.getDocuments().get(0);
                bank = bankSnapshot.toObject(Bank.class);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return bank;
    }
}