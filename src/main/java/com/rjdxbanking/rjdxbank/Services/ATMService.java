package com.rjdxbanking.rjdxbank.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.ATMChange;

public class ATMService {
    Firestore db = FirestoreClient.getFirestore();

    // Given a userId find the list of accounts associated with it.
    public ATMChange getATMChange() {
        ATMChange atmChange = null;
        try {
            ApiFuture<DocumentSnapshot> apiFuture = db.collection("atmcash").document("nsyQIbYFXQDviqTEicrU").get();
            atmChange = apiFuture.get().toObject(ATMChange.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return atmChange;
    }

    public void updateATMChange(ATMChange atmcash) {
        try {
            DocumentReference docref = db.collection("atmcash").document("nsyQIbYFXQDviqTEicrU");
            docref.update("tenDollars", atmcash.getTenDollars());
            docref.update("fiftyDollars", atmcash.getFiftyDollars());
            docref.update("hundredDollars", atmcash.getHundredDollars());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
