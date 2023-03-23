package com.rjdxbanking.rjdxbank.Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

public class Bank {
    @DocumentId
    private String Id;

    @PropertyName("bankName")
    private String bankName;

    @PropertyName("bankRoute")
    private double bankRoute;

    @PropertyName("isLocal")
    private boolean isLocal;

    public String getId() {
        return Id;
    }

    public String getBankName() {
        return bankName;
    }

    public double getBankRoute() {
        return bankRoute;
    }

    public boolean isLocal() {
        return isLocal;
    }


}
